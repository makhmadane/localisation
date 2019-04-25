import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    BoutiqueComponent,
    BoutiqueDetailComponent,
    BoutiqueUpdateComponent,
    BoutiqueDeletePopupComponent,
    BoutiqueDeleteDialogComponent,
    boutiqueRoute,
    boutiquePopupRoute
} from './';

const ENTITY_STATES = [...boutiqueRoute, ...boutiquePopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BoutiqueComponent,
        BoutiqueDetailComponent,
        BoutiqueUpdateComponent,
        BoutiqueDeleteDialogComponent,
        BoutiqueDeletePopupComponent
    ],
    entryComponents: [BoutiqueComponent, BoutiqueUpdateComponent, BoutiqueDeleteDialogComponent, BoutiqueDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaBoutiqueModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
