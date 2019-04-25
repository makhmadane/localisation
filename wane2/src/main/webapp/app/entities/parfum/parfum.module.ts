import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    ParfumComponent,
    ParfumDetailComponent,
    ParfumUpdateComponent,
    ParfumDeletePopupComponent,
    ParfumDeleteDialogComponent,
    parfumRoute,
    parfumPopupRoute
} from './';

const ENTITY_STATES = [...parfumRoute, ...parfumPopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ParfumComponent, ParfumDetailComponent, ParfumUpdateComponent, ParfumDeleteDialogComponent, ParfumDeletePopupComponent],
    entryComponents: [ParfumComponent, ParfumUpdateComponent, ParfumDeleteDialogComponent, ParfumDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaParfumModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
