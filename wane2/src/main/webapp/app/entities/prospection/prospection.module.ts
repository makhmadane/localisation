import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    ProspectionComponent,
    ProspectionDetailComponent,
    ProspectionUpdateComponent,
    ProspectionDeletePopupComponent,
    ProspectionDeleteDialogComponent,
    prospectionRoute,
    prospectionPopupRoute
} from './';

const ENTITY_STATES = [...prospectionRoute, ...prospectionPopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProspectionComponent,
        ProspectionDetailComponent,
        ProspectionUpdateComponent,
        ProspectionDeleteDialogComponent,
        ProspectionDeletePopupComponent
    ],
    entryComponents: [ProspectionComponent, ProspectionUpdateComponent, ProspectionDeleteDialogComponent, ProspectionDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaProspectionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
