
import { TerminalWatchJob }      from './model.component';

let job: TerminalWatchJob;

describe('Model: TerminalWatchJob', () => {
    beforeEach(() => {
        job = new TerminalWatchJob();
    });

    it('should allow setting flags', () => {
        job.activeOnMonday = true;
        job.activeOnFriday = true;
        expect(job.activeOnWeekdays).toEqual(['MONDAY', 'FRIDAY']);
    });

    it('should allow disabling flag again', () => {
        job.activeOnSaturday = true;
        job.activeOnTuesday = true;
        expect(job.activeOnWeekdays).toEqual(['SATURDAY', 'TUESDAY']);
        job.activeOnSaturday = false;
        expect(job.activeOnWeekdays).toEqual(['TUESDAY']);
    });

    it('should return correct flag from array', () => {
        job.activeOnWeekdays = ['SUNDAY', 'THURSDAY', 'WEDNESDAY'];
        expect(job.activeOnMonday).toEqual(false);
        expect(job.activeOnTuesday).toEqual(false);
        expect(job.activeOnWednesday).toEqual(true);
        expect(job.activeOnThursday).toEqual(true);
        expect(job.activeOnFriday).toEqual(false);
        expect(job.activeOnSaturday).toEqual(false);
        expect(job.activeOnSunday).toEqual(true);
    });
});
