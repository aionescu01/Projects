import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {GrandprixsComponent} from './grandprixs/grandprixs.component';
import {GrandprixsRoutingModule} from './grandprixs-routing.module';
import {MatButtonModule, MatCardModule, MatDialogModule, MatIconModule, MatTableModule} from '@angular/material';
import {GrandprixComponent} from './grandprix/grandprix.component';


@NgModule({
  declarations: [GrandprixsComponent, GrandprixComponent],
  imports: [
    CommonModule,
    GrandprixsRoutingModule,
    MatTableModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    MatDialogModule
  ]
})
export class GrandprixsModule { }
