import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPlcComponent } from './app-plc.component';

describe('AppPlcComponent', () => {
  let component: AppPlcComponent;
  let fixture: ComponentFixture<AppPlcComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppPlcComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPlcComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
