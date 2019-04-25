import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    ReglementComponent,
    ReglementDetailComponent,
    ReglementUpdateComponent,
    ReglementDeletePopupComponent,
    ReglementDeleteDialogComponent,
    reglementRoute,
    reglementPopupRoute
} from './';

const ENTITY_STATES = [...reglementRoute, ...reglementPopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReglementComponent,
        ReglementDetailComponent,
        ReglementUpdateComponent,
        ReglementDeleteDialogComponent,
        ReglementDeletePopupComponent
    ],
    entryComponents: [ReglementComponent, ReglementUpdateComponent, ReglementDeleteDialogComponent, ReglementDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaReglementModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
