import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogAddEditConstructorsponsorComponent } from './dialog-add-edit-constructorsponsor.component';

describe('DialogAddEditConstructorsponsorComponent', () => {
  let component: DialogAddEditConstructorsponsorComponent;
  let fixture: ComponentFixture<DialogAddEditConstructorsponsorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogAddEditConstructorsponsorComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogAddEditConstructorsponsorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
