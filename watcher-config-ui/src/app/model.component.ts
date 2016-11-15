export class TerminalWatchJob {

    terminalId : string;

    url : string;

    activeOnWeekdays : string[] = new Array<string>();

    private fromHour : number;

    private toHour : number;

    private _hourRange : number[] = [10,12];

    get hourRange() : number[] {
        return this._hourRange;
    }

    set hourRange(range : number[]) {
        this._hourRange = range;
        this.fromHour = range[0];
        this.toHour = range[1];
    }

    get hourRangeStart() : number {
        return this.fromHour;
    }

    set hourRangeStart(fromHour : number) {
        this._hourRange = [fromHour, this._hourRange[1]];
        this.fromHour = fromHour;
    }

    get hourRangeEnd() : number {
        return this.toHour;
    }

    set hourRangeEnd(toHour : number) {
        this._hourRange = [this._hourRange[0], toHour];
        this.toHour = toHour;
    }

    get activeOnMonday() {
        return this.getWeekdayFlag("MONDAY");
    }

    set activeOnMonday(v:boolean) {
        if (v == this.activeOnMonday) {
            return;
        }
        this.setWeekdayFlag("MONDAY", v);
    }

    get activeOnTuesday() {
        return this.getWeekdayFlag("TUESDAY");
    }

    set activeOnTuesday(v:boolean) {
        if (v == this.activeOnTuesday) {
            return;
        }
        this.setWeekdayFlag("TUESDAY", v);
    }

    get activeOnWednesday() {
        return this.getWeekdayFlag("WEDNESDAY");
    }

    set activeOnWednesday(v:boolean) {
        if (v == this.activeOnWednesday) {
            return;
        }
        this.setWeekdayFlag("WEDNESDAY", v);
    }

    get activeOnThursday() {
        return this.getWeekdayFlag("THURSDAY");
    }

    set activeOnThursday(v:boolean) {
        if (v == this.activeOnThursday) {
            return;
        }
        this.setWeekdayFlag("THURSDAY", v);
    }

    get activeOnFriday() {
        return this.getWeekdayFlag("FRIDAY");
    }

    set activeOnFriday(v:boolean) {
        if (v == this.activeOnFriday) {
            return;
        }
        this.setWeekdayFlag("FRIDAY", v);
    }

    get activeOnSaturday() {
        return this.getWeekdayFlag("SATURDAY");
    }

    set activeOnSaturday(v:boolean) {
        if (v == this.activeOnSaturday) {
            return;
        }
        this.setWeekdayFlag("SATURDAY", v);
    }

    get activeOnSunday() {
        return this.getWeekdayFlag("SUNDAY");
    }

    set activeOnSunday(v:boolean) {
        if (v == this.activeOnSunday) {
            return;
        }
        this.setWeekdayFlag("SUNDAY", v);
    }

    private getWeekdayFlag(name:string) : boolean {
        return this.activeOnWeekdays.indexOf(name) >= 0;
    }

    private setWeekdayFlag(name:string, v:boolean) {
        if (v) {
            this.activeOnWeekdays = this.activeOnWeekdays.concat(name);
        } else {
            this.activeOnWeekdays = this.activeOnWeekdays.splice(this.activeOnWeekdays.indexOf(name,1));
        }
    }

}