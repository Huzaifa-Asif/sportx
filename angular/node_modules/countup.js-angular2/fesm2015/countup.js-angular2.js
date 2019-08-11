import { Directive, ElementRef, Input, Output, HostListener, EventEmitter, NgModule } from '@angular/core';
import * as CountUp from 'countup.js';

/**
 * @fileoverview added by tsickle
 * @suppress {checkTypes,extraRequire,missingReturn,uselessCode} checked by tsc
 */
class CountUpDirective {
    /**
     * @param {?} el
     */
    constructor(el) {
        this.el = el;
        // on-complete event emitter
        this.complete = new EventEmitter();
    }
    // Re-animate if preference is set.
    /**
     * @return {?}
     */
    onClick() {
        if (this.reanimateOnClick) {
            this.animate();
        }
    }
    /**
     * @param {?} changes
     * @return {?}
     */
    ngOnChanges(changes) {
        if (changes.endVal && changes.endVal.currentValue) {
            this.countUp = this.createCountUp();
            this.animate();
        }
    }
    /**
     * @return {?}
     */
    createCountUp() {
        /** @type {?} */
        const start = this.startVal || 0;
        /** @type {?} */
        const duration = this.duration || 2;
        /** @type {?} */
        const decimals = this.decimals || 0;
        if (!this.duration) {
            this.duration = duration;
        }
        // construct countUp
        /** @type {?} */
        let countUp = new CountUp(this.el.nativeElement, start, this.endVal, decimals, duration, this.options);
        /** @type {?} */
        const diff = Math.abs(this.endVal - start);
        // make easing smoother for large numbers
        if (diff > 999) {
            /** @type {?} */
            const up = (this.endVal > start) ? -1 : 1;
            countUp = new CountUp(this.el.nativeElement, start, this.endVal + (up * 100), decimals, duration / 2, this.options);
        }
        return countUp;
    }
    /**
     * @return {?}
     */
    animate() {
        this.countUp.reset();
        if (this.endVal > 999) {
            this.countUp.start(() => this.countUp.update(this.endVal));
            setTimeout(() => {
                this.complete.emit();
            }, this.duration * 1000);
        }
        else {
            this.countUp.start(() => {
                this.complete.emit();
            });
        }
    }
}
CountUpDirective.decorators = [
    { type: Directive, args: [{
                selector: '[countUp]'
            },] }
];
CountUpDirective.ctorParameters = () => [
    { type: ElementRef }
];
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

/**
 * @fileoverview added by tsickle
 * @suppress {checkTypes,extraRequire,missingReturn,uselessCode} checked by tsc
 */
class CountUpModule {
}
CountUpModule.decorators = [
    { type: NgModule, args: [{
                imports: [],
                declarations: [CountUpDirective],
                exports: [CountUpDirective]
            },] }
];

/**
 * @fileoverview added by tsickle
 * @suppress {checkTypes,extraRequire,missingReturn,uselessCode} checked by tsc
 */

/**
 * @fileoverview added by tsickle
 * @suppress {checkTypes,extraRequire,missingReturn,uselessCode} checked by tsc
 */

export { CountUpModule, CountUpDirective as ɵa };

//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY291bnR1cC5qcy1hbmd1bGFyMi5qcy5tYXAiLCJzb3VyY2VzIjpbIm5nOi8vY291bnR1cC5qcy1hbmd1bGFyMi9saWIvY291bnQtdXAuZGlyZWN0aXZlLnRzIiwibmc6Ly9jb3VudHVwLmpzLWFuZ3VsYXIyL2xpYi9jb3VudC11cC5tb2R1bGUudHMiXSwic291cmNlc0NvbnRlbnQiOlsiaW1wb3J0IHtcbiAgRGlyZWN0aXZlLFxuICBFbGVtZW50UmVmLFxuICBJbnB1dCxcbiAgT3V0cHV0LFxuICBIb3N0TGlzdGVuZXIsXG4gIEV2ZW50RW1pdHRlcixcbiAgT25DaGFuZ2VzLFxuICBTaW1wbGVDaGFuZ2VzXG59IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xuaW1wb3J0ICogYXMgQ291bnRVcCBmcm9tICdjb3VudHVwLmpzJztcblxuQERpcmVjdGl2ZSh7XG4gIHNlbGVjdG9yOiAnW2NvdW50VXBdJ1xufSlcbmV4cG9ydCBjbGFzcyBDb3VudFVwRGlyZWN0aXZlIGltcGxlbWVudHMgT25DaGFuZ2VzIHtcblxuICBjb3VudFVwOiBhbnk7XG4gIC8vIE9wdGlvbmFsIGV4dHJhIGNvbmZpZ3VyYXRpb24sIHN1Y2ggYXMgZWFzaW5nLlxuICBASW5wdXQoJ2NvdW50VXAnKSBvcHRpb25zOiBhbnk7XG5cbiAgLy8gT3B0aW9uYWwgc3RhcnQgdmFsdWUgZm9yIHRoZSBjb3VudC4gRGVmYXVsdHMgdG8gemVyby5cbiAgQElucHV0KCkgc3RhcnRWYWw6IG51bWJlcjtcblxuICAvLyB0aGUgbnVtYmVyIHRvIGNvdW50IHRvXG4gIEBJbnB1dCgpIGVuZFZhbDogbnVtYmVyO1xuXG4gIC8vIE9wdGlvbmFsIGR1cmF0aW9uIG9mIHRoZSBhbmltYXRpb24gaW4gc2Vjb25kcy4gRGVmYXVsdCBpcyAyLlxuICBASW5wdXQoKSBkdXJhdGlvbjogbnVtYmVyO1xuXG4gIC8vIE9wdGlvbmFsIG51bWJlciBvZiBkZWNpbWFsIHBsYWNlcy4gRGVmYXVsdCBpcyAyLlxuICBASW5wdXQoKSBkZWNpbWFsczogbnVtYmVyO1xuICBcbiAgLy8gT3B0aW9uYWwgZmxhZyBmb3Igc3BlY2lmeWluZyB3aGV0aGVyIHRoZSBlbGVtZW50IHNob3VsZCByZS1hbmltYXRlIHdoZW4gY2xpY2tlZC5cbiAgLy8gRGVmYXVsdCBpcyB0cnVlLlxuICBASW5wdXQoKSByZWFuaW1hdGVPbkNsaWNrOiBib29sZWFuO1xuXG4gIC8vIFJlLWFuaW1hdGUgaWYgcHJlZmVyZW5jZSBpcyBzZXQuXG4gIEBIb3N0TGlzdGVuZXIoJ2NsaWNrJylcbiAgb25DbGljaygpIHtcbiAgICBpZiAodGhpcy5yZWFuaW1hdGVPbkNsaWNrKSB7XG4gICAgICB0aGlzLmFuaW1hdGUoKTtcbiAgICB9XG4gIH1cblxuICAvLyBvbi1jb21wbGV0ZSBldmVudCBlbWl0dGVyXG4gIEBPdXRwdXQoKSBjb21wbGV0ZSA9IG5ldyBFdmVudEVtaXR0ZXI8dm9pZD4oKTtcblxuICBjb25zdHJ1Y3Rvcihwcml2YXRlIGVsOiBFbGVtZW50UmVmKSB7fVxuXG4gIG5nT25DaGFuZ2VzKGNoYW5nZXM6IFNpbXBsZUNoYW5nZXMpIHtcbiAgICBpZiAoY2hhbmdlcy5lbmRWYWwgJiYgY2hhbmdlcy5lbmRWYWwuY3VycmVudFZhbHVlKSB7XG4gICAgICB0aGlzLmNvdW50VXAgPSB0aGlzLmNyZWF0ZUNvdW50VXAoKTtcbiAgICAgIHRoaXMuYW5pbWF0ZSgpO1xuICAgIH1cbiAgfVxuXG4gIHByaXZhdGUgY3JlYXRlQ291bnRVcCgpOiBhbnkge1xuICAgIGNvbnN0IHN0YXJ0ID0gdGhpcy5zdGFydFZhbCB8fCAwO1xuICAgIGNvbnN0IGR1cmF0aW9uID0gdGhpcy5kdXJhdGlvbiB8fCAyO1xuICAgIGNvbnN0IGRlY2ltYWxzID0gdGhpcy5kZWNpbWFscyB8fCAwO1xuXG4gICAgaWYgKCF0aGlzLmR1cmF0aW9uKSB7XG4gICAgICB0aGlzLmR1cmF0aW9uID0gZHVyYXRpb247XG4gICAgfVxuXG4gICAgLy8gY29uc3RydWN0IGNvdW50VXBcbiAgICBsZXQgY291bnRVcCA9IG5ldyBDb3VudFVwKHRoaXMuZWwubmF0aXZlRWxlbWVudCwgc3RhcnQsIHRoaXMuZW5kVmFsLCBkZWNpbWFscywgZHVyYXRpb24sIHRoaXMub3B0aW9ucyk7XG4gICAgY29uc3QgZGlmZiA9IE1hdGguYWJzKHRoaXMuZW5kVmFsIC0gc3RhcnQpO1xuICAgIC8vIG1ha2UgZWFzaW5nIHNtb290aGVyIGZvciBsYXJnZSBudW1iZXJzXG4gICAgaWYgKGRpZmYgPiA5OTkpIHtcbiAgICAgIGNvbnN0IHVwID0gKHRoaXMuZW5kVmFsID4gc3RhcnQpID8gLTEgOiAxO1xuICAgICAgY291bnRVcCA9IG5ldyBDb3VudFVwKHRoaXMuZWwubmF0aXZlRWxlbWVudCwgc3RhcnQsIHRoaXMuZW5kVmFsICsgKHVwICogMTAwKSwgZGVjaW1hbHMsIGR1cmF0aW9uIC8gMiwgdGhpcy5vcHRpb25zKTtcbiAgICB9XG5cbiAgICByZXR1cm4gY291bnRVcDtcbiAgfVxuXG4gIHByaXZhdGUgYW5pbWF0ZSgpIHtcbiAgICB0aGlzLmNvdW50VXAucmVzZXQoKTtcbiAgICBpZiAodGhpcy5lbmRWYWwgPiA5OTkpIHtcbiAgICAgIHRoaXMuY291bnRVcC5zdGFydCgoKSA9PiB0aGlzLmNvdW50VXAudXBkYXRlKHRoaXMuZW5kVmFsKSk7XG4gICAgICBzZXRUaW1lb3V0KCgpID0+IHtcbiAgICAgICAgdGhpcy5jb21wbGV0ZS5lbWl0KCk7XG4gICAgICB9LCB0aGlzLmR1cmF0aW9uICogMTAwMCk7XG4gICAgfSBlbHNlIHtcbiAgICAgIHRoaXMuY291bnRVcC5zdGFydCgoKSA9PiB7XG4gICAgICAgIHRoaXMuY29tcGxldGUuZW1pdCgpO1xuICAgICAgfSk7XG4gICAgfVxuICB9XG59XG4iLCJpbXBvcnQgeyBOZ01vZHVsZSB9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xuaW1wb3J0IHsgQ291bnRVcERpcmVjdGl2ZSB9IGZyb20gJy4vY291bnQtdXAuZGlyZWN0aXZlJztcblxuQE5nTW9kdWxlKHtcbiAgaW1wb3J0czogW10sXG4gIGRlY2xhcmF0aW9uczogW0NvdW50VXBEaXJlY3RpdmVdLFxuICBleHBvcnRzOiBbQ291bnRVcERpcmVjdGl2ZV1cbn0pXG5leHBvcnQgY2xhc3MgQ291bnRVcE1vZHVsZSB7IH1cbiJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7Ozs7O0FBQUE7Ozs7SUFnREUsWUFBb0IsRUFBYztRQUFkLE9BQUUsR0FBRixFQUFFLENBQVk7O1FBRnhCLGFBQVEsR0FBRyxJQUFJLFlBQVksRUFBUSxDQUFDO0tBRVI7Ozs7O0lBVHRDLE9BQU87UUFDTCxJQUFJLElBQUksQ0FBQyxnQkFBZ0IsRUFBRTtZQUN6QixJQUFJLENBQUMsT0FBTyxFQUFFLENBQUM7U0FDaEI7S0FDRjs7Ozs7SUFPRCxXQUFXLENBQUMsT0FBc0I7UUFDaEMsSUFBSSxPQUFPLENBQUMsTUFBTSxJQUFJLE9BQU8sQ0FBQyxNQUFNLENBQUMsWUFBWSxFQUFFO1lBQ2pELElBQUksQ0FBQyxPQUFPLEdBQUcsSUFBSSxDQUFDLGFBQWEsRUFBRSxDQUFDO1lBQ3BDLElBQUksQ0FBQyxPQUFPLEVBQUUsQ0FBQztTQUNoQjtLQUNGOzs7O0lBRU8sYUFBYTs7Y0FDYixLQUFLLEdBQUcsSUFBSSxDQUFDLFFBQVEsSUFBSSxDQUFDOztjQUMxQixRQUFRLEdBQUcsSUFBSSxDQUFDLFFBQVEsSUFBSSxDQUFDOztjQUM3QixRQUFRLEdBQUcsSUFBSSxDQUFDLFFBQVEsSUFBSSxDQUFDO1FBRW5DLElBQUksQ0FBQyxJQUFJLENBQUMsUUFBUSxFQUFFO1lBQ2xCLElBQUksQ0FBQyxRQUFRLEdBQUcsUUFBUSxDQUFDO1NBQzFCOzs7WUFHRyxPQUFPLEdBQUcsSUFBSSxPQUFPLENBQUMsSUFBSSxDQUFDLEVBQUUsQ0FBQyxhQUFhLEVBQUUsS0FBSyxFQUFFLElBQUksQ0FBQyxNQUFNLEVBQUUsUUFBUSxFQUFFLFFBQVEsRUFBRSxJQUFJLENBQUMsT0FBTyxDQUFDOztjQUNoRyxJQUFJLEdBQUcsSUFBSSxDQUFDLEdBQUcsQ0FBQyxJQUFJLENBQUMsTUFBTSxHQUFHLEtBQUssQ0FBQzs7UUFFMUMsSUFBSSxJQUFJLEdBQUcsR0FBRyxFQUFFOztrQkFDUixFQUFFLEdBQUcsQ0FBQyxJQUFJLENBQUMsTUFBTSxHQUFHLEtBQUssSUFBSSxDQUFDLENBQUMsR0FBRyxDQUFDO1lBQ3pDLE9BQU8sR0FBRyxJQUFJLE9BQU8sQ0FBQyxJQUFJLENBQUMsRUFBRSxDQUFDLGFBQWEsRUFBRSxLQUFLLEVBQUUsSUFBSSxDQUFDLE1BQU0sSUFBSSxFQUFFLEdBQUcsR0FBRyxDQUFDLEVBQUUsUUFBUSxFQUFFLFFBQVEsR0FBRyxDQUFDLEVBQUUsSUFBSSxDQUFDLE9BQU8sQ0FBQyxDQUFDO1NBQ3JIO1FBRUQsT0FBTyxPQUFPLENBQUM7S0FDaEI7Ozs7SUFFTyxPQUFPO1FBQ2IsSUFBSSxDQUFDLE9BQU8sQ0FBQyxLQUFLLEVBQUUsQ0FBQztRQUNyQixJQUFJLElBQUksQ0FBQyxNQUFNLEdBQUcsR0FBRyxFQUFFO1lBQ3JCLElBQUksQ0FBQyxPQUFPLENBQUMsS0FBSyxDQUFDLE1BQU0sSUFBSSxDQUFDLE9BQU8sQ0FBQyxNQUFNLENBQUMsSUFBSSxDQUFDLE1BQU0sQ0FBQyxDQUFDLENBQUM7WUFDM0QsVUFBVSxDQUFDO2dCQUNULElBQUksQ0FBQyxRQUFRLENBQUMsSUFBSSxFQUFFLENBQUM7YUFDdEIsRUFBRSxJQUFJLENBQUMsUUFBUSxHQUFHLElBQUksQ0FBQyxDQUFDO1NBQzFCO2FBQU07WUFDTCxJQUFJLENBQUMsT0FBTyxDQUFDLEtBQUssQ0FBQztnQkFDakIsSUFBSSxDQUFDLFFBQVEsQ0FBQyxJQUFJLEVBQUUsQ0FBQzthQUN0QixDQUFDLENBQUM7U0FDSjtLQUNGOzs7WUE5RUYsU0FBUyxTQUFDO2dCQUNULFFBQVEsRUFBRSxXQUFXO2FBQ3RCOzs7WUFaQyxVQUFVOzs7c0JBaUJULEtBQUssU0FBQyxTQUFTO3VCQUdmLEtBQUs7cUJBR0wsS0FBSzt1QkFHTCxLQUFLO3VCQUdMLEtBQUs7K0JBSUwsS0FBSztzQkFHTCxZQUFZLFNBQUMsT0FBTzt1QkFRcEIsTUFBTTs7Ozs7OztBQzlDVDs7O1lBR0MsUUFBUSxTQUFDO2dCQUNSLE9BQU8sRUFBRSxFQUFFO2dCQUNYLFlBQVksRUFBRSxDQUFDLGdCQUFnQixDQUFDO2dCQUNoQyxPQUFPLEVBQUUsQ0FBQyxnQkFBZ0IsQ0FBQzthQUM1Qjs7Ozs7Ozs7Ozs7Ozs7OyJ9