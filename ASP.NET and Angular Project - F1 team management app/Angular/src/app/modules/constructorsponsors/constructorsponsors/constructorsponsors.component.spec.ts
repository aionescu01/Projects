import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConstructorsponsorsComponent } from './constructorsponsors.component';

describe('ConstructorsponsorsComponent', () => {
  let component: ConstructorsponsorsComponent;
  let fixture: ComponentFixture<ConstructorsponsorsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConstructorsponsorsComponent ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConstructorsponsorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
