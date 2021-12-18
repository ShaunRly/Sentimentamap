import { TweetData } from './../Models/TweetData.model';
import { Subscription } from 'rxjs';
import { Bubble } from './../Models/bubbleData.model';
import { DatabaseServiceService } from './../database-service.service';
import { CalenderService } from './../calender.service';
import { Component, OnInit } from '@angular/core';
import { ChartType, ChartOptions, ChartDataSets } from 'chart.js';
import { SingleDataSet, Label, monkeyPatchChartJsLegend, monkeyPatchChartJsTooltip } from 'ng2-charts';

@Component({
  selector: 'app-scatter-chart',
  templateUrl: './scatter-chart.component.html',
  styleUrls: ['./scatter-chart.component.css']
})
export class ScatterChartComponent implements OnInit {

  public subscription:Subscription

  public scatterChartOptions: ChartOptions = {
    responsive: true,
    scales: {
      xAxes: [{
        type: 'time'
      }]
    }
  };

  public scatterChartData: ChartDataSets[] = [
    {
      data: [
      ],
      label: 'Individual Tweets',
      pointRadius: 10,
      backgroundColor: 'hex(#14151f)',
      pointBackgroundColor: function(context) {
        var index = context.dataIndex!;
        var value = context.dataset!.data![index]!;
        let y:number = (value as Chart.ChartPoint).y as number;
        var depthPercentage = (y + 1) / 2;
        var blueShift = Math.round( (255 * depthPercentage * 1.05));
        var redShift = 255 - blueShift;
        var colorShift =  [redShift - 20, blueShift + 20, 15]
        return 'rgb(' + colorShift[0] + ', '+ colorShift[2] + ',' + colorShift[1] + ')'
      }
    },
  ];
  public scatterChartType: ChartType = 'scatter';
  constructor(private calenderService:CalenderService, private databaseServiceService:DatabaseServiceService) {
    this.subscription = this.calenderService.newDateTimeEvent.subscribe(data => {
      this.getChartData(data)
    })
    monkeyPatchChartJsTooltip();
    monkeyPatchChartJsLegend();
  }

  async ngOnInit() {
    this.calenderService.initEvent()
  }

  async getChartData(data:any){
    var arg:string = data;
    var argsList:string[] = arg.split("/")
    var bubbleData:TweetData[] = await this.databaseServiceService.getTweetsForTopic(argsList[0], argsList[1])
    console.log(bubbleData)
    this.genChart(bubbleData)
  }

  genChart(data:TweetData[]){
    this.scatterChartData[0].data = []
    for (const [index, dataPoint] of data.entries()){
      (this.scatterChartData[0].data as object[]).push(
        {x: dataPoint.createdAt, y: dataPoint.sentiment.compound}
      )
    }
  }

  colorShift(yValue:number){
    var depthPercentage = (yValue + 1) / 2;
    var redShift = Math.round( (255 * depthPercentage * 1.05));
    var blueShift = 255 - redShift;
    return [redShift - 20, blueShift + 20, 15]
  }
}
