import {Component} from '@angular/core';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';

/**
 * @title Drag&Drop sorting
 */
@Component({
    selector: 'jhi-drag',
    templateUrl: 'drag-and-drop.component.html',
    styleUrls: ['drag-and-drop.component.scss'],
})
export class jhiCdkDragDropConnectedSortingGroupExampleComponent {
    movies = [
        'Episode I - The Phantom Menace',
        'Episode II - Attack of the Clones',
        'Episode III - Revenge of the Sith',
        'Episode IV - A New Hope',
        'Episode V - The Empire Strikes Back',
        'Episode VI - Return of the Jedi',
        'Episode VII - The Force Awakens',
        'Episode VIII - The Last Jedi'
    ];

    drop(event: CdkDragDrop<string[]>) {
        console.log("test")
        moveItemInArray(this.movies, event.previousIndex, event.currentIndex);
    }
}
