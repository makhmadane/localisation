import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    SecteurComponent,
    SecteurDetailComponent,
    SecteurUpdateComponent,
    SecteurDeletePopupComponent,
    SecteurDeleteDialogComponent,
    secteurRoute,
    secteurPopupRoute
} from './';

const ENTITY_STATES = [...secteurRoute, ...secteurPopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SecteurComponent,
        SecteurDetailComponent,
        SecteurUpdateComponent,
        SecteurDeleteDialogComponent,
        SecteurDeletePopupComponent
    ],
    entryComponents: [SecteurComponent, SecteurUpdateComponent, SecteurDeleteDialogComponent, SecteurDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaSecteurModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
