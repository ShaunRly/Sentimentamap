import { Component, OnInit } from '@angular/core';
import * as Matter from 'matter-js';
import {Engine, Runner, Render, World, MouseConstraint, Bodies, Composite, Mouse, Composites, Body, Vector, Constraint} from 'matter-js';

@Component({
  selector: 'app-scales',
  templateUrl: './scales.component.html',
  styleUrls: ['./scales.component.css']
})
export class ScalesComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    this.catapult();
  }


catapult():void {
  

    // create engine
    var engine = Engine.create(),
        world = engine.world;

    // create renderer
    var render = Render.create({
        element: document.body,
        engine: engine,
        options: {
            width: 800,
            height: 600,
        }
    });

    Render.run(render);

    // create runner
    var runner = Runner.create();
    Runner.run(runner, engine);

    // add bodies
    var group = Body.nextGroup(true);

    var stack = Composites.stack(250, 255, 1, 6, 0, 0, function(x:any, y:any) {
        return Bodies.rectangle(x, y, 30, 30);
    });

    var scale = Bodies.rectangle(400, 420, 520, 20, { collisionFilter: { group: group } })
    var basketL = Bodies.rectangle(150, 310, 20, 200, { collisionFilter: { group: group } })
    var basketR = Bodies.rectangle(650, 310, 20, 200, { collisionFilter: { group: group } })

    var compoundScale = Body.create({
      parts: [scale, basketL, basketR]
    })

    var floor = Bodies.rectangle(400, 600, 800, 50, { isStatic: true, render: { fillStyle: '#060a19' } })
    var pivot = Bodies.rectangle(400, 535, 20, 580, { isStatic: true, collisionFilter: { group: group }, render: { fillStyle: '#060a19' } })

    Composite.add(world, [
        stack,
        compoundScale,
        floor, 
        pivot,
        Bodies.circle(560, 100, 50, { density: 0.005 }),
        Constraint.create({ 
            bodyA: scale,
            pointB: Vector.clone(compoundScale.position),
            stiffness: 1
        }),
    ]);

    // add mouse control
    var mouse = Mouse.create(render.canvas),
        mouseConstraint = MouseConstraint.create(engine, {
            mouse: mouse,
            constraint: {
                stiffness: 0.2,
                render: {
                    visible: false
                }
            }
        });

    Composite.add(world, mouseConstraint);

    // keep the mouse in sync with rendering
    render.mouse = mouse;

    // fit the render viewport to the scene
    Render.lookAt(render, {
        min: { x: 0, y: 0 },
        max: { x: 800, y: 600 }
    });

  
};

}
