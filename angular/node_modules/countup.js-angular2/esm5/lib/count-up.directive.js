/**
 * @fileoverview added by tsickle
 * @suppress {checkTypes,extraRequire,missingReturn,uselessCode} checked by tsc
 */
import { Directive, ElementRef, Input, Output, HostListener, EventEmitter } from '@angular/core';
import * as CountUp from 'countup.js';
var CountUpDirective = /** @class */ (function () {
    function CountUpDirective(el) {
        this.el = el;
        // on-complete event emitter
        this.complete = new EventEmitter();
    }
    // Re-animate if preference is set.
    // Re-animate if preference is set.
    /**
     * @return {?}
     */
    CountUpDirective.prototype.onClick = 
    // Re-animate if preference is set.
    /**
     * @return {?}
     */
    function () {
        if (this.reanimateOnClick) {
            this.animate();
        }
    };
    /**
     * @param {?} changes
     * @return {?}
     */
    CountUpDirective.prototype.ngOnChanges = /**
     * @param {?} changes
     * @return {?}
     */
    function (changes) {
        if (changes.endVal && changes.endVal.currentValue) {
            this.countUp = this.createCountUp();
            this.animate();
        }
    };
    /**
     * @return {?}
     */
    CountUpDirective.prototype.createCountUp = /**
     * @return {?}
     */
    function () {
        /** @type {?} */
        var start = this.startVal || 0;
        /** @type {?} */
        var duration = this.duration || 2;
        /** @type {?} */
        var decimals = this.decimals || 0;
        if (!this.duration) {
            this.duration = duration;
        }
        // construct countUp
        /** @type {?} */
        var countUp = new CountUp(this.el.nativeElement, start, this.endVal, decimals, duration, this.options);
        /** @type {?} */
        var diff = Math.abs(this.endVal - start);
        // make easing smoother for large numbers
        if (diff > 999) {
            /** @type {?} */
            var up = (this.endVal > start) ? -1 : 1;
            countUp = new CountUp(this.el.nativeElement, start, this.endVal + (up * 100), decimals, duration / 2, this.options);
        }
        return countUp;
    };
    /**
     * @return {?}
     */
    CountUpDirective.prototype.animate = /**
     * @return {?}
     */
    function () {
        var _this = this;
        this.countUp.reset();
        if (this.endVal > 999) {
            this.countUp.start(function () { return _this.countUp.update(_this.endVal); });
            setTimeout(function () {
                _this.complete.emit();
            }, this.duration * 1000);
        }
        else {
            this.countUp.start(function () {
                _this.complete.emit();
            });
        }
    };
    CountUpDirective.decorators = [
        { type: Directive, args: [{
                    selector: '[countUp]'
                },] }
    ];
    CountUpDirective.ctorParameters = function () { return [
        { type: ElementRef }
    ]; };
    CountUpDirective.propDecorators = {
        options: [{ type: Input, args: ['countUp',] }],
        startVal: [{ type: Input }],
        endVal: [{ type: Input }],
        duration: [{ type: Input }],
        decimals: [{ type: Input }],
        reanimateOnClick: [{ type: Input }],
        onClick: [{ type: HostListener, args: ['click',] }],
        complete: [{ type: Output }]
    };
    return CountUpDirective;
}());
export { CountUpDirective };
if (false) {
    /** @type {?} */
    CountUpDirective.prototype.countUp;
    /** @type {?} */
    CountUpDirective.prototype.options;
    /** @type {?} */
    CountUpDirective.prototype.startVal;
    /** @type {?} */
    CountUpDirective.prototype.endVal;
    /** @type {?} */
    CountUpDirective.prototype.duration;
    /** @type {?} */
    CountUpDirective.prototype.decimals;
    /** @type {?} */
    CountUpDirective.prototype.reanimateOnClick;
    /** @type {?} */
    CountUpDirective.prototype.complete;
    /** @type {?} */
    CountUpDirective.prototype.el;
}
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY291bnQtdXAuZGlyZWN0aXZlLmpzIiwic291cmNlUm9vdCI6Im5nOi8vY291bnR1cC5qcy1hbmd1bGFyMi8iLCJzb3VyY2VzIjpbImxpYi9jb3VudC11cC5kaXJlY3RpdmUudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7OztBQUFBLE9BQU8sRUFDTCxTQUFTLEVBQ1QsVUFBVSxFQUNWLEtBQUssRUFDTCxNQUFNLEVBQ04sWUFBWSxFQUNaLFlBQVksRUFHYixNQUFNLGVBQWUsQ0FBQztBQUN2QixPQUFPLEtBQUssT0FBTyxNQUFNLFlBQVksQ0FBQztBQUV0QztJQW9DRSwwQkFBb0IsRUFBYztRQUFkLE9BQUUsR0FBRixFQUFFLENBQVk7UUFIbEMsNEJBQTRCO1FBQ2xCLGFBQVEsR0FBRyxJQUFJLFlBQVksRUFBUSxDQUFDO0lBRVQsQ0FBQztJQVh0QyxtQ0FBbUM7Ozs7O0lBRW5DLGtDQUFPOzs7OztJQURQO1FBRUUsSUFBSSxJQUFJLENBQUMsZ0JBQWdCLEVBQUU7WUFDekIsSUFBSSxDQUFDLE9BQU8sRUFBRSxDQUFDO1NBQ2hCO0lBQ0gsQ0FBQzs7Ozs7SUFPRCxzQ0FBVzs7OztJQUFYLFVBQVksT0FBc0I7UUFDaEMsSUFBSSxPQUFPLENBQUMsTUFBTSxJQUFJLE9BQU8sQ0FBQyxNQUFNLENBQUMsWUFBWSxFQUFFO1lBQ2pELElBQUksQ0FBQyxPQUFPLEdBQUcsSUFBSSxDQUFDLGFBQWEsRUFBRSxDQUFDO1lBQ3BDLElBQUksQ0FBQyxPQUFPLEVBQUUsQ0FBQztTQUNoQjtJQUNILENBQUM7Ozs7SUFFTyx3Q0FBYTs7O0lBQXJCOztZQUNRLEtBQUssR0FBRyxJQUFJLENBQUMsUUFBUSxJQUFJLENBQUM7O1lBQzFCLFFBQVEsR0FBRyxJQUFJLENBQUMsUUFBUSxJQUFJLENBQUM7O1lBQzdCLFFBQVEsR0FBRyxJQUFJLENBQUMsUUFBUSxJQUFJLENBQUM7UUFFbkMsSUFBSSxDQUFDLElBQUksQ0FBQyxRQUFRLEVBQUU7WUFDbEIsSUFBSSxDQUFDLFFBQVEsR0FBRyxRQUFRLENBQUM7U0FDMUI7OztZQUdHLE9BQU8sR0FBRyxJQUFJLE9BQU8sQ0FBQyxJQUFJLENBQUMsRUFBRSxDQUFDLGFBQWEsRUFBRSxLQUFLLEVBQUUsSUFBSSxDQUFDLE1BQU0sRUFBRSxRQUFRLEVBQUUsUUFBUSxFQUFFLElBQUksQ0FBQyxPQUFPLENBQUM7O1lBQ2hHLElBQUksR0FBRyxJQUFJLENBQUMsR0FBRyxDQUFDLElBQUksQ0FBQyxNQUFNLEdBQUcsS0FBSyxDQUFDO1FBQzFDLHlDQUF5QztRQUN6QyxJQUFJLElBQUksR0FBRyxHQUFHLEVBQUU7O2dCQUNSLEVBQUUsR0FBRyxDQUFDLElBQUksQ0FBQyxNQUFNLEdBQUcsS0FBSyxDQUFDLENBQUMsQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDO1lBQ3pDLE9BQU8sR0FBRyxJQUFJLE9BQU8sQ0FBQyxJQUFJLENBQUMsRUFBRSxDQUFDLGFBQWEsRUFBRSxLQUFLLEVBQUUsSUFBSSxDQUFDLE1BQU0sR0FBRyxDQUFDLEVBQUUsR0FBRyxHQUFHLENBQUMsRUFBRSxRQUFRLEVBQUUsUUFBUSxHQUFHLENBQUMsRUFBRSxJQUFJLENBQUMsT0FBTyxDQUFDLENBQUM7U0FDckg7UUFFRCxPQUFPLE9BQU8sQ0FBQztJQUNqQixDQUFDOzs7O0lBRU8sa0NBQU87OztJQUFmO1FBQUEsaUJBWUM7UUFYQyxJQUFJLENBQUMsT0FBTyxDQUFDLEtBQUssRUFBRSxDQUFDO1FBQ3JCLElBQUksSUFBSSxDQUFDLE1BQU0sR0FBRyxHQUFHLEVBQUU7WUFDckIsSUFBSSxDQUFDLE9BQU8sQ0FBQyxLQUFLLENBQUMsY0FBTSxPQUFBLEtBQUksQ0FBQyxPQUFPLENBQUMsTUFBTSxDQUFDLEtBQUksQ0FBQyxNQUFNLENBQUMsRUFBaEMsQ0FBZ0MsQ0FBQyxDQUFDO1lBQzNELFVBQVUsQ0FBQztnQkFDVCxLQUFJLENBQUMsUUFBUSxDQUFDLElBQUksRUFBRSxDQUFDO1lBQ3ZCLENBQUMsRUFBRSxJQUFJLENBQUMsUUFBUSxHQUFHLElBQUksQ0FBQyxDQUFDO1NBQzFCO2FBQU07WUFDTCxJQUFJLENBQUMsT0FBTyxDQUFDLEtBQUssQ0FBQztnQkFDakIsS0FBSSxDQUFDLFFBQVEsQ0FBQyxJQUFJLEVBQUUsQ0FBQztZQUN2QixDQUFDLENBQUMsQ0FBQztTQUNKO0lBQ0gsQ0FBQzs7Z0JBOUVGLFNBQVMsU0FBQztvQkFDVCxRQUFRLEVBQUUsV0FBVztpQkFDdEI7OztnQkFaQyxVQUFVOzs7MEJBaUJULEtBQUssU0FBQyxTQUFTOzJCQUdmLEtBQUs7eUJBR0wsS0FBSzsyQkFHTCxLQUFLOzJCQUdMLEtBQUs7bUNBSUwsS0FBSzswQkFHTCxZQUFZLFNBQUMsT0FBTzsyQkFRcEIsTUFBTTs7SUE2Q1QsdUJBQUM7Q0FBQSxBQS9FRCxJQStFQztTQTVFWSxnQkFBZ0I7OztJQUUzQixtQ0FBYTs7SUFFYixtQ0FBK0I7O0lBRy9CLG9DQUEwQjs7SUFHMUIsa0NBQXdCOztJQUd4QixvQ0FBMEI7O0lBRzFCLG9DQUEwQjs7SUFJMUIsNENBQW1DOztJQVduQyxvQ0FBOEM7O0lBRWxDLDhCQUFzQiIsInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7XG4gIERpcmVjdGl2ZSxcbiAgRWxlbWVudFJlZixcbiAgSW5wdXQsXG4gIE91dHB1dCxcbiAgSG9zdExpc3RlbmVyLFxuICBFdmVudEVtaXR0ZXIsXG4gIE9uQ2hhbmdlcyxcbiAgU2ltcGxlQ2hhbmdlc1xufSBmcm9tICdAYW5ndWxhci9jb3JlJztcbmltcG9ydCAqIGFzIENvdW50VXAgZnJvbSAnY291bnR1cC5qcyc7XG5cbkBEaXJlY3RpdmUoe1xuICBzZWxlY3RvcjogJ1tjb3VudFVwXSdcbn0pXG5leHBvcnQgY2xhc3MgQ291bnRVcERpcmVjdGl2ZSBpbXBsZW1lbnRzIE9uQ2hhbmdlcyB7XG5cbiAgY291bnRVcDogYW55O1xuICAvLyBPcHRpb25hbCBleHRyYSBjb25maWd1cmF0aW9uLCBzdWNoIGFzIGVhc2luZy5cbiAgQElucHV0KCdjb3VudFVwJykgb3B0aW9uczogYW55O1xuXG4gIC8vIE9wdGlvbmFsIHN0YXJ0IHZhbHVlIGZvciB0aGUgY291bnQuIERlZmF1bHRzIHRvIHplcm8uXG4gIEBJbnB1dCgpIHN0YXJ0VmFsOiBudW1iZXI7XG5cbiAgLy8gdGhlIG51bWJlciB0byBjb3VudCB0b1xuICBASW5wdXQoKSBlbmRWYWw6IG51bWJlcjtcblxuICAvLyBPcHRpb25hbCBkdXJhdGlvbiBvZiB0aGUgYW5pbWF0aW9uIGluIHNlY29uZHMuIERlZmF1bHQgaXMgMi5cbiAgQElucHV0KCkgZHVyYXRpb246IG51bWJlcjtcblxuICAvLyBPcHRpb25hbCBudW1iZXIgb2YgZGVjaW1hbCBwbGFjZXMuIERlZmF1bHQgaXMgMi5cbiAgQElucHV0KCkgZGVjaW1hbHM6IG51bWJlcjtcbiAgXG4gIC8vIE9wdGlvbmFsIGZsYWcgZm9yIHNwZWNpZnlpbmcgd2hldGhlciB0aGUgZWxlbWVudCBzaG91bGQgcmUtYW5pbWF0ZSB3aGVuIGNsaWNrZWQuXG4gIC8vIERlZmF1bHQgaXMgdHJ1ZS5cbiAgQElucHV0KCkgcmVhbmltYXRlT25DbGljazogYm9vbGVhbjtcblxuICAvLyBSZS1hbmltYXRlIGlmIHByZWZlcmVuY2UgaXMgc2V0LlxuICBASG9zdExpc3RlbmVyKCdjbGljaycpXG4gIG9uQ2xpY2soKSB7XG4gICAgaWYgKHRoaXMucmVhbmltYXRlT25DbGljaykge1xuICAgICAgdGhpcy5hbmltYXRlKCk7XG4gICAgfVxuICB9XG5cbiAgLy8gb24tY29tcGxldGUgZXZlbnQgZW1pdHRlclxuICBAT3V0cHV0KCkgY29tcGxldGUgPSBuZXcgRXZlbnRFbWl0dGVyPHZvaWQ+KCk7XG5cbiAgY29uc3RydWN0b3IocHJpdmF0ZSBlbDogRWxlbWVudFJlZikge31cblxuICBuZ09uQ2hhbmdlcyhjaGFuZ2VzOiBTaW1wbGVDaGFuZ2VzKSB7XG4gICAgaWYgKGNoYW5nZXMuZW5kVmFsICYmIGNoYW5nZXMuZW5kVmFsLmN1cnJlbnRWYWx1ZSkge1xuICAgICAgdGhpcy5jb3VudFVwID0gdGhpcy5jcmVhdGVDb3VudFVwKCk7XG4gICAgICB0aGlzLmFuaW1hdGUoKTtcbiAgICB9XG4gIH1cblxuICBwcml2YXRlIGNyZWF0ZUNvdW50VXAoKTogYW55IHtcbiAgICBjb25zdCBzdGFydCA9IHRoaXMuc3RhcnRWYWwgfHwgMDtcbiAgICBjb25zdCBkdXJhdGlvbiA9IHRoaXMuZHVyYXRpb24gfHwgMjtcbiAgICBjb25zdCBkZWNpbWFscyA9IHRoaXMuZGVjaW1hbHMgfHwgMDtcblxuICAgIGlmICghdGhpcy5kdXJhdGlvbikge1xuICAgICAgdGhpcy5kdXJhdGlvbiA9IGR1cmF0aW9uO1xuICAgIH1cblxuICAgIC8vIGNvbnN0cnVjdCBjb3VudFVwXG4gICAgbGV0IGNvdW50VXAgPSBuZXcgQ291bnRVcCh0aGlzLmVsLm5hdGl2ZUVsZW1lbnQsIHN0YXJ0LCB0aGlzLmVuZFZhbCwgZGVjaW1hbHMsIGR1cmF0aW9uLCB0aGlzLm9wdGlvbnMpO1xuICAgIGNvbnN0IGRpZmYgPSBNYXRoLmFicyh0aGlzLmVuZFZhbCAtIHN0YXJ0KTtcbiAgICAvLyBtYWtlIGVhc2luZyBzbW9vdGhlciBmb3IgbGFyZ2UgbnVtYmVyc1xuICAgIGlmIChkaWZmID4gOTk5KSB7XG4gICAgICBjb25zdCB1cCA9ICh0aGlzLmVuZFZhbCA+IHN0YXJ0KSA/IC0xIDogMTtcbiAgICAgIGNvdW50VXAgPSBuZXcgQ291bnRVcCh0aGlzLmVsLm5hdGl2ZUVsZW1lbnQsIHN0YXJ0LCB0aGlzLmVuZFZhbCArICh1cCAqIDEwMCksIGRlY2ltYWxzLCBkdXJhdGlvbiAvIDIsIHRoaXMub3B0aW9ucyk7XG4gICAgfVxuXG4gICAgcmV0dXJuIGNvdW50VXA7XG4gIH1cblxuICBwcml2YXRlIGFuaW1hdGUoKSB7XG4gICAgdGhpcy5jb3VudFVwLnJlc2V0KCk7XG4gICAgaWYgKHRoaXMuZW5kVmFsID4gOTk5KSB7XG4gICAgICB0aGlzLmNvdW50VXAuc3RhcnQoKCkgPT4gdGhpcy5jb3VudFVwLnVwZGF0ZSh0aGlzLmVuZFZhbCkpO1xuICAgICAgc2V0VGltZW91dCgoKSA9PiB7XG4gICAgICAgIHRoaXMuY29tcGxldGUuZW1pdCgpO1xuICAgICAgfSwgdGhpcy5kdXJhdGlvbiAqIDEwMDApO1xuICAgIH0gZWxzZSB7XG4gICAgICB0aGlzLmNvdW50VXAuc3RhcnQoKCkgPT4ge1xuICAgICAgICB0aGlzLmNvbXBsZXRlLmVtaXQoKTtcbiAgICAgIH0pO1xuICAgIH1cbiAgfVxufVxuIl19