import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appBtnHover]'
})
export class BtnHoverDirective {

  constructor(
    public el: ElementRef
  ) {
  }

  @HostListener('mouseenter') onMouseEnter() {
    this.highlight('#B27A43');
  }

  @HostListener('mouseleave') onMouseLeave() {
    this.highlight('');
  }

  private highlight(color: string) {
    this.el.nativeElement.style.backgroundColor = color;
  }
}
