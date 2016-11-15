import { Component, OnInit } from '@angular/core';
import { TrainWatcherConfigurationService } from './service.component';
import { TerminalWatchJob } from './model.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  providers : [TrainWatcherConfigurationService]
})
export class AppComponent implements OnInit {
    detailsPanelId : 'jobDetails';
    jobs : TerminalWatchJob[];
    selectedJob : TerminalWatchJob;

    constructor(private service : TrainWatcherConfigurationService) {
        this.selectedJob = null;
    }

    onEdit(job : TerminalWatchJob) : void {
        this.selectedJob = new TerminalWatchJob();
        this.selectedJob.active = job.active;
        this.selectedJob.terminalId = job.terminalId;
        console.log(job.hourRange); 
        this.selectedJob.hourRange = job.hourRange;
        this.selectedJob.url = job.url;
        this.selectedJob.activeOnWeekdays = job.activeOnWeekdays;
    }

    ngOnInit() : void  {
        this.service.findConfigurations().then(j => this.jobs = j);
    }

    toggleJobActivation(job : TerminalWatchJob) : void {
        this.service.saveWatchJob(job);
    }

    addJob() : void {
        this.selectedJob = new TerminalWatchJob();
        this.selectedJob.hourRange = [0, 24];
        this.selectedJob.active = true;
        this.selectedJob.activeOnMonday = true;
        this.selectedJob.activeOnTuesday = true;
        this.selectedJob.activeOnWednesday = true;
        this.selectedJob.activeOnThursday = true;
        this.selectedJob.activeOnFriday = true;
        
    }

    deleteJob(job : TerminalWatchJob) : void {
        this.service.deleteWatchJob(job).then(j => this.ngOnInit());
    }

    saveJob() : void {
        this.service.saveWatchJob(this.selectedJob).then(() => {this.selectedJob = null; this.ngOnInit();});
    }

    cancelJob() : void {
        this.selectedJob = null;
    }

 }
