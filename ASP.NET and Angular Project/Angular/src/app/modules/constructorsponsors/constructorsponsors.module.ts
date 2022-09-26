import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ConstructorsponsorsComponent} from './constructorsponsors/constructorsponsors.component';
import {ConstructorsponsorsRoutingModule} from './constructorsponsors-routing.module';
import {MatButtonModule, MatCardModule, MatDialogModule, MatIconModule, MatTableModule} from '@angular/material';
import {ConstructorsponsorComponent} from './constructorsponsor/constructorsponsor.component';
import {SharedModule} from '../shared/shared.module';


@NgModule({
  declarations: [ConstructorsponsorsComponent, ConstructorsponsorComponent],
    imports: [
        CommonModule,
        ConstructorsponsorsRoutingModule,
        MatTableModule,
        MatIconModule,
        MatCardModule,
        MatButtonModule,
        MatDialogModule,
        SharedModule
    ]
})
export class ConstructorsponsorsModule { }
