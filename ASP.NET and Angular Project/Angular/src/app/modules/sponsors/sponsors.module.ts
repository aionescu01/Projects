import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SponsorsComponent} from './sponsors/sponsors.component';
import {SponsorsRoutingModule} from './sponsors-routing.module';
import {MatButtonModule, MatCardModule, MatDialogModule, MatIconModule, MatTableModule} from '@angular/material';
import {SponsorComponent} from './sponsor/sponsor.component';


@NgModule({
  declarations: [SponsorsComponent, SponsorComponent],
  imports: [
    CommonModule,
    SponsorsRoutingModule,
    MatTableModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    MatDialogModule
  ]
})
export class SponsorsModule { }
