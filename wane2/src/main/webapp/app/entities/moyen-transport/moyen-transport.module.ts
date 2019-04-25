import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    MoyenTransportComponent,
    MoyenTransportDetailComponent,
    MoyenTransportUpdateComponent,
    MoyenTransportDeletePopupComponent,
    MoyenTransportDeleteDialogComponent,
    moyenTransportRoute,
    moyenTransportPopupRoute
} from './';

const ENTITY_STATES = [...moyenTransportRoute, ...moyenTransportPopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MoyenTransportComponent,
        MoyenTransportDetailComponent,
        MoyenTransportUpdateComponent,
        MoyenTransportDeleteDialogComponent,
        MoyenTransportDeletePopupComponent
    ],
    entryComponents: [
        MoyenTransportComponent,
        MoyenTransportUpdateComponent,
        MoyenTransportDeleteDialogComponent,
        MoyenTransportDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaMoyenTransportModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
