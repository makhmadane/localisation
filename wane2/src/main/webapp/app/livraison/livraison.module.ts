import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { YesColaSharedModule } from 'app/shared';
import {
    CommandeComponent,
    CommandeDetailComponent,
    CommandeDeletePopupComponent,
    LivraisonUpdateComponent,
    CommandeDeleteDialogComponent,
    commandeRoute,
    commandePopupRoute
} from './';

const ENTITY_STATES = [...commandeRoute, ...commandePopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES),DragDropModule],
    declarations: [
        CommandeComponent,
        CommandeDetailComponent,
        LivraisonUpdateComponent,
        CommandeDeleteDialogComponent,
        CommandeDeletePopupComponent
    ],
    entryComponents: [CommandeComponent, LivraisonUpdateComponent, CommandeDeleteDialogComponent, CommandeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommandeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
