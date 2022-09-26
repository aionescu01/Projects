import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ConstructorsComponent} from './constructors/constructors.component';
import {ConstructorsRoutingModule} from './constructors-routing.module';
import {MatButtonModule, MatCardModule, MatDialogModule, MatIconModule, MatTableModule} from '@angular/material';
import {ConstructorComponent} from './constructor/constructor.component';


@NgModule({
  declarations: [ConstructorsComponent, ConstructorComponent],
  imports: [
    CommonModule,
    ConstructorsRoutingModule,
    MatTableModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    MatDialogModule
  ]
})
export class ConstructorsModule { }
