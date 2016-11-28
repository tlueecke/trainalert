export class TerminalWatchJob {

    active: boolean;

    terminalId: string;

    url: string;

    activeOnWeekdays: string[] = new Array<string>();

    private _hourRangeStart: number;

    private _hourRangeEnd: number;

    private _range: number[] = new Array<number>(2);

    get hourRangeStart(): number {
        return this._hourRangeStart;
    }

    set hourRangeStart(start: number) {
        this._hourRangeStart = start;
        this._range = [this._hourRangeStart, this._hourRangeEnd];
    }

    get hourRangeEnd(): number {
        return this._hourRangeEnd;
    }

    set hourRangeEnd(end: number) {
        this._hourRangeEnd = end;
        this._range = [this._hourRangeStart, this._hourRangeEnd];
    }

    get hourRange(): number[] {
        return this._range;
    }

    set hourRange(range: number[]) {
        this._hourRangeStart = range[0];
        this._hourRangeEnd = range[1];
        this._range[0] = range[0];
        this._range[1] = range[1];
    }

    get activeOnMonday() {
        return this.getWeekdayFlag('MONDAY');
    }

    set activeOnMonday(v: boolean) {
        if (v === this.activeOnMonday) {
            return;
        }
        this.setWeekdayFlag('MONDAY', v);
    }

    get activeOnTuesday() {
        return this.getWeekdayFlag('TUESDAY');
    }

    set activeOnTuesday(v: boolean) {
        if (v === this.activeOnTuesday) {
            return;
        }
        this.setWeekdayFlag('TUESDAY', v);
    }

    get activeOnWednesday() {
        return this.getWeekdayFlag('WEDNESDAY');
    }

    set activeOnWednesday(v: boolean) {
        if (v === this.activeOnWednesday) {
            return;
        }
        this.setWeekdayFlag('WEDNESDAY', v);
    }

    get activeOnThursday() {
        return this.getWeekdayFlag('THURSDAY');
    }

    set activeOnThursday(v: boolean) {
        if (v === this.activeOnThursday) {
            return;
        }
        this.setWeekdayFlag('THURSDAY', v);
    }

    get activeOnFriday() {
        return this.getWeekdayFlag('FRIDAY');
    }

    set activeOnFriday(v: boolean) {
        if (v === this.activeOnFriday) {
            return;
        }
        this.setWeekdayFlag('FRIDAY', v);
    }

    get activeOnSaturday() {
        return this.getWeekdayFlag('SATURDAY');
    }

    set activeOnSaturday(v: boolean) {
        if (v === this.activeOnSaturday) {
            return;
        }
        this.setWeekdayFlag('SATURDAY', v);
    }

    get activeOnSunday() {
        return this.getWeekdayFlag('SUNDAY');
    }

    set activeOnSunday(v: boolean) {
        if (v === this.activeOnSunday) {
            return;
        }
        this.setWeekdayFlag('SUNDAY', v);
    }

    private getWeekdayFlag(name: string): boolean {
        return this.activeOnWeekdays.indexOf(name) >= 0;
    }

    private setWeekdayFlag(name: string, v: boolean) {
        if (v) {
            this.activeOnWeekdays = this.activeOnWeekdays.concat(name);
        } else {
            this.activeOnWeekdays = this.activeOnWeekdays.splice(this.activeOnWeekdays.indexOf(name, 1));
        }
    }

}
