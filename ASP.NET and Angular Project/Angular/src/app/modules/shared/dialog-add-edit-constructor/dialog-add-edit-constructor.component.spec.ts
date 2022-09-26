import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogAddEditConstructorComponent } from './dialog-add-edit-constructor.component';

describe('DialogAddEditConstructorComponent', () => {
  let component: DialogAddEditConstructorComponent;
  let fixture: ComponentFixture<DialogAddEditConstructorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogAddEditConstructorComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogAddEditConstructorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
