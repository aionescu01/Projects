import {Component, Inject, OnInit} from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ConstructorsponsorsService } from '../../../services/constructorsponsors.service';

@Component({
  selector: 'app-dialog-add-edit-constructorsponsor',
  templateUrl: './dialog-add-edit-constructorsponsor.component.html',
  styleUrls: ['./dialog-add-edit-constructorsponsor.component.scss']
})
export class DialogAddEditConstructorsponsorComponent implements OnInit {

  public title: string;
  public isEditable: boolean;
  public constructorsponsorsForm: FormGroup = new FormGroup({
    sponsorId: new FormControl(),
    constructorname: new FormControl(''),
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<DialogAddEditConstructorsponsorComponent>,
    private constructorsponsorsService: ConstructorsponsorsService,
  ) {
    console.log(this.data);
    if (this.data.constructorsponsor) {
      this.title = 'Edit Constructorsponsor';
      this.constructorsponsorsForm.patchValue(this.data.constructorsponsor);
      this.isEditable = true;
    } else {
      this.title = 'Add Constructorsponsor';
      this.isEditable = false;
    }
  }

  get sponsorId(): AbstractControl {
    return this.constructorsponsorsForm.get('sponsorId');
  }
  get constructorName(): AbstractControl {
    return this.constructorsponsorsForm.get('constructorName');
  }
  get sponsorName(): AbstractControl {
    return this.constructorsponsorsForm.get('sponsorName');
  }


  ngOnInit() {
  }

  public closeDialog(): void {
    this.dialogRef.close();
  }

  public saveData(): void {
    console.log(this.constructorsponsorsForm.value);
    if (this.isEditable === false) {
      this.constructorsponsorsService.createConstructorsponsor(this.constructorsponsorsForm.value).subscribe(() => {
        this.dialogRef.close(this.constructorsponsorsForm.value);
      });
    } else {
      this.constructorsponsorsService.editConstructorsponsor(this.constructorsponsorsForm.value).subscribe(() => {
        this.dialogRef.close(this.constructorsponsorsForm.value);
      });
    }
  }

}
