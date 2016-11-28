import { Injectable } from '@angular/core';
import { TerminalWatchJob} from './model.component';
import { Headers, Http, Response, RequestOptions } from '@angular/http';
import { environment } from '../environments/environment';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class TrainWatcherConfigurationService {

    constructor(private http: Http) {
    }

    findConfigurations(): Promise<TerminalWatchJob[]> {
        return this.http.get(environment.configUrl)
            .toPromise()
            .then(this.mapToJob)
            .catch(this.handleError);
    }

    saveWatchJob(job: TerminalWatchJob): Promise<TerminalWatchJob> {
        let headers = new Headers();
        headers.set('Content-Type', 'application/json');
        let options = new RequestOptions({headers: headers});
        if (job.url) {
            return this.http.put(job.url, job, options)
                .toPromise()
                .then(() => job)
                .catch(this.handleError);
        } else {
            return this.http.post(environment.configUrl, job, options)
                .toPromise()
                .then(() => job)
                .catch(this.handleError);
        }
    }

    deleteWatchJob(job: TerminalWatchJob): Promise<TerminalWatchJob> {
        return this.http.delete(job.url)
            .toPromise()
            .then(() => job)
            .catch(this.handleError);
    }

    mapToDts(job: TerminalWatchJob): TerminalWatchJobDts {
        let dts = new TerminalWatchJobDts();
        dts.active = job.active;
        dts.activeOnWeekdays = job.activeOnWeekdays;
        dts.fromHour = job.hourRange[0];
        dts.toHour = job.hourRange[1];
        dts.terminalId = job.terminalId;
        return dts;
    }

    mapToJob(response: Response): TerminalWatchJob[] {
        let data = response.json();
        let dtsJobs = data._embedded.terminalWatchJob as TerminalWatchJobDts[];
        let jobs = new Array<TerminalWatchJob>(dtsJobs.length);
        let i = 0;
        for (i = 0; i < jobs.length; i++) {
            let job = new TerminalWatchJob();
            job.active = dtsJobs[i].active;
            job.activeOnWeekdays = dtsJobs[i].activeOnWeekdays;
            job.hourRange = [dtsJobs[i].fromHour, dtsJobs[i].toHour];
            job.terminalId = dtsJobs[i].terminalId;
            job.url = dtsJobs[i]._links.self.href;
            jobs[i] = job;
        }
        return jobs;
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }

}

class TerminalWatchJobDts {

    active: boolean;

    terminalId: string;

    activeOnWeekdays: string[] = new Array<string>();

    fromHour: number;

    toHour: number;

    _links: Links;

}

class Links {
    self: Link;
}

class Link {
    href: string;
}
