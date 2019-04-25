import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    CommuneComponent,
    CommuneDetailComponent,
    CommuneUpdateComponent,
    CommuneDeletePopupComponent,
    CommuneDeleteDialogComponent,
    communeRoute,
    communePopupRoute
} from './';

const ENTITY_STATES = [...communeRoute, ...communePopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommuneComponent,
        CommuneDetailComponent,
        CommuneUpdateComponent,
        CommuneDeleteDialogComponent,
        CommuneDeletePopupComponent
    ],
    entryComponents: [CommuneComponent, CommuneUpdateComponent, CommuneDeleteDialogComponent, CommuneDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaCommuneModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
