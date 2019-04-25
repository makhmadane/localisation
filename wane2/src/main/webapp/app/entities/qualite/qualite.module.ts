import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    QualiteComponent,
    QualiteDetailComponent,
    QualiteUpdateComponent,
    QualiteDeletePopupComponent,
    QualiteDeleteDialogComponent,
    qualiteRoute,
    qualitePopupRoute
} from './';

const ENTITY_STATES = [...qualiteRoute, ...qualitePopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QualiteComponent,
        QualiteDetailComponent,
        QualiteUpdateComponent,
        QualiteDeleteDialogComponent,
        QualiteDeletePopupComponent
    ],
    entryComponents: [QualiteComponent, QualiteUpdateComponent, QualiteDeleteDialogComponent, QualiteDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaQualiteModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
