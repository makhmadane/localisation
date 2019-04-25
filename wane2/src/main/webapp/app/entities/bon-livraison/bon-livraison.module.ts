import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    BonLivraisonComponent,
    BonLivraisonDetailComponent,
    BonLivraisonUpdateComponent,
    BonLivraisonDeletePopupComponent,
    BonLivraisonDeleteDialogComponent,
    bonLivraisonRoute,
    bonLivraisonPopupRoute
} from './';

const ENTITY_STATES = [...bonLivraisonRoute, ...bonLivraisonPopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BonLivraisonComponent,
        BonLivraisonDetailComponent,
        BonLivraisonUpdateComponent,
        BonLivraisonDeleteDialogComponent,
        BonLivraisonDeletePopupComponent
    ],
    entryComponents: [
        BonLivraisonComponent,
        BonLivraisonUpdateComponent,
        BonLivraisonDeleteDialogComponent,
        BonLivraisonDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaBonLivraisonModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
