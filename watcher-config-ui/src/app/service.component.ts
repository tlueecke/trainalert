import { Injectable } from '@angular/core';
import { TerminalWatchJob} from './model.component';
import { Headers, Http, Response, RequestOptions } from '@angular/http';
import { environment } from '../environments/environment';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class TrainWatcherConfigurationService{

    constructor(private http : Http) {
    }

    findConfigurations() : Promise<TerminalWatchJob[]> {
        return this.http.get(environment.configUrl)
            .toPromise()
            .then(this.extractData)
            .catch(this.handleError);
    }

    saveWatchJob(job : TerminalWatchJob) : Promise<TerminalWatchJob> {
        let headers = new Headers();
        headers.set('Content-Type','application/json');
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
                .catch(this.handleError);;
        }
    }

    deleteWatchJob(job : TerminalWatchJob) : Promise<TerminalWatchJob> {
        return this.http.delete(job.url)
            .toPromise()
            .then(() => job)
            .catch(this.handleError);
    }

    extractData(response : Response) : TerminalWatchJob[] {
        let data = response.json();
        let rawJobs = data._embedded.terminalWatchJob
        let jobs = rawJobs as TerminalWatchJob[];
        let i = 0;
        for (i = 0; i < jobs.length; i++) {
            jobs[i].url = rawJobs[i]._links.self.href;
        }
        return jobs;
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }

}