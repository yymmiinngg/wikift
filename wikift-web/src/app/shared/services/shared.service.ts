import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class SharedService {

    sidebarVisible: boolean;
    sidebarVisibilitySubject: Subject<boolean> = new Subject<boolean>();

    toggleSidebarVisibilty() {
        this.sidebarVisible = !this.sidebarVisible;
        this.sidebarVisibilitySubject.next(this.sidebarVisible);
    }

    maTheme: string;
    maThemeSubject: Subject<string> = new Subject<string>();

    setTheme(color) {
        this.maTheme = color;
        this.maThemeSubject.next(this.maTheme);
    }

    constructor()  {
        this.sidebarVisible = true;
        this.maTheme = 'green';
    }

}
