import { Injectable, Output, EventEmitter } from '@angular/core';
import 'datejs'

@Injectable({
  providedIn: 'root'
})
export class CalenderService {

  private date:Date = <any>Date

  @Output() newDateTimeEvent = new EventEmitter<string>();

  constructor() {
    this.date = Date.today()
    this.date.setTime(Date.now())
  }

  initEvent(){
    this.dateTimeEvent(this.date.toString("yyyy-MM-dd"), this.getTimeRange())
  }

  getDate(){
    return this.date.toString("yyyy-MM-dd")
  }

  getTimeRange(){
    var time1 = this.date.getHours() + ((this.date.getMinutes() < 30) ? ":00" : ":30")
    var time2 = (this.date.getMinutes() < 30 ? this.date.getHours() : (this.date.getHours() + 1)) + ((this.date.getMinutes() < 30) ? ":30" : ":00")
    console.log(time1, time2)
    return time1 + " - " + time2
  }

  setDay(direction:String, timeRange:String){
    if(this.getDate() == Date.today().toString("yyyy-MM-dd")){
      if(direction == "backwards") {
        this.date.addDays(-1)
        this.dateTimeEvent(this.getDate(), timeRange)
      }
    } else {
      direction == "forwards" ? this.date.addDays(1) :  this.date.addDays(-1)
      this.dateTimeEvent(this.getDate(), timeRange)
    }
  }

  previousDayEndOf(){
    this.date = this.date.addDays(-1)
    this.date.setHours(23, 30)
    this.dateTimeEvent(this.getDate().toString(), this.getTimeRange())
  }

  nextDayStartOf(){
    if(this.getDate() != Date.today().toString("yyyy-MM-dd")){
      this.date = this.date.addDays(1)
      this.date.setHours(0, 0)
      console.log(this.date)
      this.dateTimeEvent(this.getDate().toString(), this.getTimeRange())
    }
  }

  dateTimeEvent (date:String, time:String){
    var time1 = ""
    var time2 = ""
    if(time.length == 11){
      time1 = date + " 0" + time.substring(0, 4)
      time2 = date + " 0" + time.substring(7, 13)
    }
    if(time.length == 12){
      time1 = date + " 0" + time.substring(0, 4)
      time2 = date + " " + time.substring(7, 13)
    }
    if(time.length == 13) {
      time1 = date + " " + time.substring(0, 5)
      time2 = date + "" + time.substring(7, 13)
    }
    this.newDateTimeEvent.emit(time1 + "/" + time2)
  }

  setTime(hours:number, mins:number){
    this.date.setHours(hours, mins)
  }
  
}
