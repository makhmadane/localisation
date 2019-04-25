import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    TypeTransportComponent,
    TypeTransportDetailComponent,
    TypeTransportUpdateComponent,
    TypeTransportDeletePopupComponent,
    TypeTransportDeleteDialogComponent,
    typeTransportRoute,
    typeTransportPopupRoute
} from './';

const ENTITY_STATES = [...typeTransportRoute, ...typeTransportPopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeTransportComponent,
        TypeTransportDetailComponent,
        TypeTransportUpdateComponent,
        TypeTransportDeleteDialogComponent,
        TypeTransportDeletePopupComponent
    ],
    entryComponents: [
        TypeTransportComponent,
        TypeTransportUpdateComponent,
        TypeTransportDeleteDialogComponent,
        TypeTransportDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaTypeTransportModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
