import {async, TestBed, getTestBed} from '@angular/core/testing';

import {TrainWatcherConfigurationService} from './service.component';
import { TerminalWatchJob } from './model.component';
import {MockBackend, MockConnection} from '@angular/http/testing';
import {Http, HttpModule, XHRBackend, BaseRequestOptions, Response, ResponseOptions} from '@angular/http';


    // http://chariotsolutions.com/blog/post/testing-angular-2-0-x-services-http-jasmine-karma/

describe('Service: TrainWatcherConfigurationService', () => {

    let mockBackend: MockBackend;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            providers: [
                TrainWatcherConfigurationService,
                MockBackend,
                BaseRequestOptions,
                {
                    provide: Http,
                    deps: [MockBackend, BaseRequestOptions],
                    useFactory: (backend: XHRBackend, defaultOptions: BaseRequestOptions) => {
                        return new Http(backend, defaultOptions);
                    }
                }
            ],
            imports: [
                HttpModule
            ]
        });

        mockBackend = getTestBed().get(MockBackend);
    }));

    it('Should find and map configurations from server', done => {

        getTestBed().compileComponents().then(() => {
            mockBackend.connections.subscribe(
                (connection : MockConnection) => {
                    expect(connection.request.url).toMatch(/\/config/);
                    connection.mockRespond(new Response(
                        new ResponseOptions({
                            body: [
                                {
                                    _embedded : {
                                        terminalWatchJob : [ {
                                        fromTerminal : 'ATST',
                                        toTerminal : 'HH',
                                        fromHour : 7,
                                        toHour : 10,
                                        active : true,
                                        activeOnWeekdays : [ 'MONDAY' ],
                                        _links : {
                                            self : {
                                            href : 'http://localhost:2222/config/1'
                                            },
                                            terminalWatchJob : {
                                            href : 'http://localhost:2222/config/1'
                                            }
                                        }
                                        } ]
                                    },
                                    _links : {
                                        self : {
                                        href : 'http://localhost:2222/config'
                                        },
                                        profile : {
                                        href : 'http://localhost:2222/profile/config'
                                        },
                                        search : {
                                        href : 'http://localhost:2222/config/search'
                                        }
                                    },
                                    page : {
                                        size : 20,
                                        totalElements : 1,
                                        totalPages : 1,
                                        number : 0
                                    }
                                }
                                ]
                        })
                    ));
                }
            );
            
            let service : TrainWatcherConfigurationService = getTestBed().get(TrainWatcherConfigurationService);

            console.log('call service');
            let jobs : Promise<TerminalWatchJob[]> = service.findConfigurations();
            console.log('test result');
            jobs.then(jobs => {
                expect(jobs.length).toBe(1);
            });
        });
    });
});