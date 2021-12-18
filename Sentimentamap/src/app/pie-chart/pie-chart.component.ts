import { Bubble } from './../Models/bubbleData.model';
import { DatabaseServiceService } from './../database-service.service';
import { CalenderService } from './../calender.service';
import { Component, OnInit } from '@angular/core';
import { ChartType, ChartOptions } from 'chart.js';
import { SingleDataSet, Label, monkeyPatchChartJsLegend, monkeyPatchChartJsTooltip } from 'ng2-charts';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent implements OnInit {

  // Pie
  public pieChartOptions: ChartOptions = {
    responsive: true,
  };
  public pieChartLabels: Label[] = []
  public pieChartData: SingleDataSet = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
  public pieChartType: ChartType = 'pie'
  public pieChartLegend = true
  public pieChartPlugins = []
  private subscription:any

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
    var bubbleData:Bubble[] = await this.databaseServiceService.getHalfHourDataPoints(argsList[0], argsList[1])
      // .subscribe(data => {
      //   var bubbleData:Bubble[] = data
      this.genChart(bubbleData)
      // }, error => console.log(error))
  }

  genChart(data:Bubble[]){
    this.pieChartData = []
    this.pieChartLabels = []
    for (var dataPoint of data){
      // Temporary Fix for results that match more than 1 rule
        if(dataPoint.matchingRule.split("/").length == 2){
        this.pieChartData.push(dataPoint.count)
        var label = dataPoint.matchingRule.split("/")[1]
        label = label.substring(0, label.length -2)
        this.pieChartLabels.push(label)
        
      }
    }
  }
  

}
