import { Bubble } from './../Models/bubbleData.model';
import { CalenderService } from './../calender.service';
import { DatabaseServiceService } from './../database-service.service';
import { Component, Input, OnInit } from '@angular/core';
import * as Matter from 'matter-js';
import {Engine, Runner, Render, World, MouseConstraint, Bodies, Composite, Mouse} from 'matter-js';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-bubbles',
  templateUrl: './bubbles.component.html',
  styleUrls: ['./bubbles.component.css']
})
export class BubblesComponent implements OnInit {

  private subscription!: Subscription;
  private bodyList:Matter.Body[]
  private bubbleList:Matter.Body[]
  private engine:Matter.Engine
  private world:Matter.World
  private targetLevels:number[] = []
  private targetLevelsProduct:number[] = []
  private labelList:string[] = []
  

  constructor(private databaseServiceService:DatabaseServiceService, private calenderService:CalenderService) {
    this.subscription = this.calenderService.newDateTimeEvent.subscribe(data => {
      this.getBubbles(data)
    })
    
    this.bodyList = []
    this.bubbleList = []
    this.engine = Engine.create()
    this.world = this.engine.world;
   }

  ngOnInit(): void {
    let canvaselement:HTMLCanvasElement = <HTMLCanvasElement> document.getElementById('matter-canvas')!
    let viewPortHeight = window.innerHeight
    let viewPortWidth = window.innerWidth
    this.demo(canvaselement, viewPortHeight, viewPortWidth)
    this.calenderService.initEvent()
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  async getBubbles(data:any){
    var arg:string = data;
    var argsList:string[] = arg.split("/")
    var bubbleData:Bubble[] = await this.databaseServiceService.getHalfHourDataPoints(argsList[0], argsList[1])
    this.addBubbles(bubbleData)
  }

  fluctuation():number {
    var plusOrMinus = Math.random() < 0.5 ? -1 : 1;
    var fluctuationStrength = Math.random() * 5 * plusOrMinus;
    return fluctuationStrength;
  }

  flucationx():number {
    var plusOrMinus = Math.random() < 0.5 ? -1 : 1;
    var fluctuationStrength = Math.random() * 0.001 * plusOrMinus;
    return fluctuationStrength;
  }

  colorShift(depth:number, canvasHeight:number){
    var depthPercentage = depth / (canvasHeight * 1.05);
    var redShift = Math.round( (255 * depthPercentage * 1.05));
    var blueShift = 255 - redShift;
    return [redShift - 20, blueShift + 20, 15]
  }

  levelFinder(compound:number){
    return ((compound - (-1)) / (1 - (-1)))
  }

  addBubbles(bubbleData:Bubble[]){
    let newBubblesList:Matter.Body[] = []
    this.targetLevels = []
    this.labelList = []
    for (var bubble of bubbleData){
      // Temporary Fix for results that match more than 1 rule
      if(bubble.matchingRule.split("/").length == 2){
        newBubblesList.push(Bodies.circle(
          Math.random() * (window.innerWidth * 0.75),
          Math.random() * (window.innerHeight * 0.75) + 100,
          Math.min(Math.max(bubble.count * 3, 10), 200),
          {restitution: 0.2}
        ))
        this.targetLevels.push(window.innerHeight - (this.levelFinder(bubble.compound) * window.innerHeight))
        this.targetLevelsProduct = this.targetLevels.map(x => x + this.fluctuation())
        var label = bubble.matchingRule.split("/")[1]
        label = label.substring(0, label.length -2)
        this.labelList.push(label)
      }
    }

    for (var oldBubble of this.bubbleList){
      let oldBubbleBody:Matter.Body = oldBubble
      Matter.Composite.remove(this.engine.world, oldBubble)
    }

    Matter.Composite.add(this.engine.world, [...newBubblesList])
    this.bubbleList = newBubblesList;
  }

  demo(canvasElement:HTMLCanvasElement, viewPortHeight:number, viewPortWidth:number):void {

    var canvasHeight = viewPortHeight
    var canvasWidth = viewPortWidth * 0.80
    var randomFlow = 0

    var textCanvas:HTMLCanvasElement = <HTMLCanvasElement> document.getElementById("text-layer")!
    textCanvas.height = viewPortHeight;
    textCanvas.width = viewPortWidth * 0.80;
    var textLayer:CanvasRenderingContext2D = textCanvas.getContext("2d")!

    var render = Render.create({
        canvas: canvasElement,
        engine: this.engine,
        options: {
            width: canvasWidth,
            height: canvasHeight,
            wireframes: false,
        },
    })
    this.engine.gravity.y = 0;


    // add mouse control
    var mouse = Mouse.create(render.canvas),
        mouseConstraint = MouseConstraint.create(this.engine, {
            mouse: mouse,
            constraint: {
                stiffness: 0.2,
                render: {
                    visible: false
                },

            }
        });

    Composite.add(this.world, mouseConstraint);

    var ground = Bodies.rectangle(viewPortWidth / 2, viewPortHeight * 1.075, viewPortWidth, 200, {
         isStatic: true,
         restitution: 0.2,
         render: {
           fillStyle : "rgb(20, 21, 31)"
         }
    })
    var ceiling = Bodies.rectangle((viewPortWidth * 0.75) / 2, -80, viewPortWidth, 200, {
      isStatic: true,
      restitution: 0.2,
      render: {
        fillStyle : "rgb(20, 21, 31)"
      }
    })
    var leftWall = Bodies.rectangle(-100, viewPortHeight / 2, 200, viewPortHeight, {
      isStatic: true,
      restitution: 0.2,
      render: {
        fillStyle : "rgb(20, 21, 31)"
      }
    })
    var rightWall = Bodies.rectangle(viewPortWidth * 0.83, viewPortHeight / 2, 100, viewPortHeight, {
      isStatic: true,
      restitution: 0.2,
      render: {
        fillStyle : "rgb(20, 21, 31)"
      }
    })

    var seekingForce = 0.00001;
    this.targetLevels = [0.3 * canvasHeight, 0.6 * canvasHeight, 0.9 * canvasHeight];
    this.targetLevelsProduct = [0.3 * canvasHeight, 0.6 * canvasHeight, 0.9 * canvasHeight];
    var fluctuationCounter = 0;
    // 60 fps * x = x seconds
    var fluctuationTimePeriod = 60 * 3;

    this.bodyList = [ground, ceiling, leftWall, rightWall]
    Matter.Composite.add(this.engine.world, [...this.bodyList])
    Matter.Runner.run(this.engine)
    Render.run(render)

    Matter.Events.on(this.engine, "afterUpdate", (event) => {
      fluctuationCounter++
      if (fluctuationCounter >= fluctuationTimePeriod){
        fluctuationCounter = 0;
        for (var z = 0; z < this.targetLevels.length; z++){
          this.targetLevelsProduct[z] = this.targetLevels[z] + this.fluctuation();
        }
      }

      textLayer.clearRect(0, 0, viewPortWidth, viewPortHeight)

      for (var i = 0; i < this.bubbleList.length; i++){
        let matterBody:Matter.Body = this.bubbleList[i];
        let colorShift = this.colorShift(this.targetLevels[i], canvasHeight)
        if (matterBody.position.y < this.targetLevelsProduct[i]){
          Matter.Body.applyForce(matterBody, matterBody.position, {x: /*BubblesComponent.flucationx()*/ 0, y: (seekingForce * Math.abs(matterBody.position.y - this.targetLevelsProduct[i])) })
        } else if (matterBody.position.y >= this.targetLevelsProduct[i]) {
          Matter.Body.applyForce(matterBody, matterBody.position, {x: /*BubblesComponent.flucationx()*/ 0, y: -(seekingForce * Math.abs(matterBody.position.y - this.targetLevelsProduct[i])) })
        }
        matterBody.render.fillStyle = 'rgb(' + colorShift[0] + ', '+ colorShift[2] + ',' + colorShift[1] + ')'
        textLayer.font = "20px Arial"
        textLayer.textAlign = "center"
        textLayer.textBaseline = "middle"
        textLayer.fillStyle = "#8c8c90"
        textLayer.fillText(this.labelList[i], matterBody.position.x, matterBody.position.y)
        //Debug
        // textLayer.fillText("" + this.targetLevelsProduct[i], matterBody.position.x, matterBody.position.y + 30)
      }
    });
}
}
