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

    onSelect(job : TerminalWatchJob) : void {
        this.selectedJob = new TerminalWatchJob();
        this.selectedJob.terminalId = job.terminalId;
        this.selectedJob.fromHour = job.fromHour;
        this.selectedJob.toHour = job.toHour;
        this.selectedJob.url = job.url;
    }

    ngOnInit() : void  {
        this.service.findConfigurations().then(j => this.jobs = j);
    }

    addJob() : void {
        this.selectedJob = new TerminalWatchJob();
    }

    deleteJob(job : TerminalWatchJob) : void {
        this.service.deleteWatchJob(job).then(j => this.ngOnInit());
    }

    saveJob() : void {
        this.service.saveWatchJob(this.selectedJob).then(() => {this.selectedJob = null; this.ngOnInit();});
    }

 }
