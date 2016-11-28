/* tslint:disable:no-unused-variable */

import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { By }           from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { AppComponent } from './app.component';
import { TrainWatcherConfigurationService }      from './service.component';
import { TerminalWatchJob }      from './model.component';
import { AppModule } from './app.module';

let cfgServiceStub = {
  findConfigurations(): Promise<TerminalWatchJob[]> {
    return Promise.resolve(new TerminalWatchJob[0]);
  }
};

let fixture: ComponentFixture<AppComponent>;
let app: AppComponent;

describe('App: WatcherConfigUi', () => {
  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [AppModule],
      providers: [{provide: TrainWatcherConfigurationService, useValue: cfgServiceStub}]
    });

    fixture = TestBed.createComponent(AppComponent);
    app = fixture.componentInstance;
  });

  it('should create the app', async(() => {
    expect(app).toBeTruthy();
  }));

  it(`selectedJob should be null`, async(() => {
    expect(app.selectedJob).toBeNull();
    let detailsForm = fixture.debugElement.query(By.css('#jobDetails'));
    expect(detailsForm).toBeNull();
  }));

  it('should render title in a h1 tag', async(() => {
    let compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Train Watcher Configuration');
  }));

  it('should create job and render details when add is clicked', () => {
    let addButton = fixture.debugElement.query(By.css('#addJob'));
    expect(addButton).not.toBeNull();
    addButton.triggerEventHandler('click', null);
    fixture.detectChanges();
    let detailsForm = fixture.debugElement.query(By.css('#detailsPanel'));
    expect(detailsForm).not.toBeNull();
    expect(app.selectedJob).not.toBeNull();
  });
});
