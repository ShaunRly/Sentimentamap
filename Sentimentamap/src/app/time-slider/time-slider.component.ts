import { CalenderService } from './../calender.service';
import { Component, OnInit } from '@angular/core';
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import { fas } from '@fortawesome/free-solid-svg-icons'
import { FormControl } from '@angular/forms';


@Component({
  selector: 'app-time-slider',
  templateUrl: './time-slider.component.html',
  styleUrls: ['./time-slider.component.css']
})
export class TimeSliderComponent implements OnInit {


  public time:String;
  public date:String;
  public rangeControl = new FormControl();

  constructor(private calenderService:CalenderService) {
    this.date = this.calenderService.getDate()
    this.time = this.calenderService.getTimeRange()
   }

  ngOnInit(): void {
    this.date = this.calenderService.getDate()
    this.time = this.calenderService.getTimeRange()
    this.rangeControl.setValue(this.timeValueOf(this.time))
  }

  changeTime(timeValue:any){
    var timeDecimal:number = timeValue / 2
    var decimal = timeDecimal - Math.floor(timeDecimal)
    var minutes = decimal == 0.5 ? ":30" : ":00"
    var timeString = Math.floor(timeDecimal) + minutes
    var timeString2 = timeString[timeString.length - 2] == "3" ? (Math.floor(timeDecimal) + 1) + ":00" : Math.floor(timeDecimal) + ":30"
    this.time = timeString + " - " + timeString2 
    var calenderHours = this.time.substring(0,2)
    var calenderMins = this.time.substring(3,5)
    this.calenderService.dateTimeEvent(this.date, this.time)
    this.calenderService.setTime(parseInt(calenderHours), parseInt(calenderMins))
  }

  changeDay(direction:String){
    this.calenderService.setDay(direction, this.time)
    this.date = this.calenderService.getDate()
    this.rangeControl.setValue(this.timeValueOf(this.time))
  }

  previousDayEndOf(){
    this.calenderService.previousDayEndOf()
    this.date = this.calenderService.getDate()
    this.time = this.calenderService.getTimeRange()
    this.rangeControl.setValue(this.timeValueOf(this.time))
  }

  nextDayStartOf(){
    this.calenderService.nextDayStartOf()
    this.date = this.calenderService.getDate()
    this.time = this.calenderService.getTimeRange()
    this.rangeControl.setValue(this.timeValueOf(this.time))
  }

  timeValueOf(time:String){
    var hours = parseInt(time.substring(0, 2))
    var minutes = time.substring(3, 5) == "30" ? 0.5 : 0
    return ((hours * 2) + minutes)
  }



}
