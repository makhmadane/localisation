import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    DepotComponent,
    DepotDetailComponent,
    DepotUpdateComponent,
    DepotDeletePopupComponent,
    DepotDeleteDialogComponent,
    depotRoute,
    depotPopupRoute
} from './';

const ENTITY_STATES = [...depotRoute, ...depotPopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [DepotComponent, DepotDetailComponent, DepotUpdateComponent, DepotDeleteDialogComponent, DepotDeletePopupComponent],
    entryComponents: [DepotComponent, DepotUpdateComponent, DepotDeleteDialogComponent, DepotDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaDepotModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
