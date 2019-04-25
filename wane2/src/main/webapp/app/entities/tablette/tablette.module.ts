import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    TabletteComponent,
    TabletteDetailComponent,
    TabletteUpdateComponent,
    TabletteDeletePopupComponent,
    TabletteDeleteDialogComponent,
    tabletteRoute,
    tablettePopupRoute
} from './';

const ENTITY_STATES = [...tabletteRoute, ...tablettePopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TabletteComponent,
        TabletteDetailComponent,
        TabletteUpdateComponent,
        TabletteDeleteDialogComponent,
        TabletteDeletePopupComponent
    ],
    entryComponents: [TabletteComponent, TabletteUpdateComponent, TabletteDeleteDialogComponent, TabletteDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaTabletteModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
