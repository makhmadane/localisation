import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    StockInitialComponent,
    StockInitialDetailComponent,
    StockInitialUpdateComponent,
    StockInitialDeletePopupComponent,
    StockInitialDeleteDialogComponent,
    stockInitialRoute,
    stockInitialPopupRoute
} from './';

const ENTITY_STATES = [...stockInitialRoute, ...stockInitialPopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockInitialComponent,
        StockInitialDetailComponent,
        StockInitialUpdateComponent,
        StockInitialDeleteDialogComponent,
        StockInitialDeletePopupComponent
    ],
    entryComponents: [
        StockInitialComponent,
        StockInitialUpdateComponent,
        StockInitialDeleteDialogComponent,
        StockInitialDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaStockInitialModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
