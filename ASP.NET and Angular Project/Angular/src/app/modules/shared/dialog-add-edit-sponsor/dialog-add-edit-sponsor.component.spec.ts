import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogAddEditSponsorComponent } from './dialog-add-edit-sponsor.component';

describe('DialogAddEditSponsorComponent', () => {
  let component: DialogAddEditSponsorComponent;
  let fixture: ComponentFixture<DialogAddEditSponsorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogAddEditSponsorComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogAddEditSponsorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
