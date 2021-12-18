import { VoteOption } from './../Models/VoteOption.model';
import { VoteCategory } from './../Models/VoteOptionsDTO';
import { VoteDBService } from './../vote-db.service';
import { Subscription } from 'rxjs';
import { DatabaseServiceService } from './../database-service.service';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { CalenderService } from '../calender.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SideBarComponent implements OnInit {

  private subscription!: Subscription
  public trendLabels!: string[]
  public placeLabels!: string[]
  public placeVoteOptions!: VoteOption[]
  public wildCardVoteOptions!: VoteOption[]

  

  constructor(private calenderService: CalenderService, private databaseService: DatabaseServiceService, private voteDBService:VoteDBService, private router:Router) {
    this.subscription = this.calenderService.newDateTimeEvent.subscribe(data => {
      this.getTracked(data)
    })
   }

  ngOnInit(): void {
    this.getVotes();
  }
  
  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  async getTracked(data:any){
    var arg:string = data;
    var argsList:string[] = arg.split("/")
    var trackedTopics:String[] = await this.databaseService.getTrackedTopics(argsList[0], argsList[1])
    this.trendLabels = []
    this.placeLabels = []
    for (var rule of trackedTopics){
      if(rule.split("/").length == 2){
        if (rule.startsWith("['Place")){
          var label = rule.split("/")[1]
          label = label.substring(0, label.length -2)
          this.placeLabels.push(label)
        }
        if (rule.startsWith("['Trend")){
          var label = rule.split("/")[1]
          label = label.substring(0, label.length -2)
          this.trendLabels.push(label)
        }
      }
    }
  }

  setDataChoice(rule:string){
    this.databaseService.setRule(rule);
    this.calenderService.initEvent();
  }

  setTopicChoice(rule:string){
    this.databaseService.setRule(rule)
    this.router.navigate(['/scatter'])
    this.calenderService.initEvent()
  }

  compare(a:VoteOption, b:VoteOption){
    if ( a.voteTally < b.voteTally ){
      return -1;
    }
    if ( a.voteTally > b.voteTally ){
      return 1;
    }
    return 0;
  }

  async getVotes() {
    let voteDBResponse:VoteCategory[] = await this.voteDBService.getVoteData();
    this.placeVoteOptions = voteDBResponse[0].results
    this.wildCardVoteOptions = voteDBResponse[1].results
    this.placeVoteOptions.sort(this.compare)
    console.log(this.placeVoteOptions, this.wildCardVoteOptions)
  }

  async castVote(option:string, category:string){
    this.voteDBService.castVote(option, category)
  }
}
