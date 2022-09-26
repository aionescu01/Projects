import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatInputModule } from '@angular/material';
import { BtnHoverDirective } from '../../btn-hover.directive';
import {DialogAddEditCarComponent} from './dialog-add-edit-car/dialog-add-edit-car.component';
import {DialogAddEditDriverComponent} from './dialog-add-edit-driver/dialog-add-edit-car.component';
import {DialogAddEditConstructorComponent} from './dialog-add-edit-constructor/dialog-add-edit-constructor.component';
import {DialogAddEditGrandprixComponent} from './dialog-add-edit-grandprix/dialog-add-edit-grandprix.component';
import {DialogAddEditSponsorComponent} from './dialog-add-edit-sponsor/dialog-add-edit-sponsor.component';
import {DialogAddEditConstructorsponsorComponent} from './dialog-add-edit-constructorsponsor/dialog-add-edit-constructorsponsor.component';


@NgModule({
  declarations: [DialogAddEditCarComponent, BtnHoverDirective, DialogAddEditDriverComponent, DialogAddEditConstructorComponent,
    DialogAddEditGrandprixComponent, DialogAddEditSponsorComponent, DialogAddEditConstructorsponsorComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule
  ],
  entryComponents: [
    DialogAddEditCarComponent, DialogAddEditDriverComponent, DialogAddEditConstructorComponent,
    DialogAddEditGrandprixComponent, DialogAddEditSponsorComponent, DialogAddEditConstructorsponsorComponent
  ],
  exports: [
    BtnHoverDirective
  ]
})
export class SharedModule { }
