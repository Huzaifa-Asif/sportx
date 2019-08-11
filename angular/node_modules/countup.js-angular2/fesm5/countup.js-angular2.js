import { Directive, ElementRef, Input, Output, HostListener, EventEmitter, NgModule } from '@angular/core';
import * as CountUp from 'countup.js';

/**
 * @fileoverview added by tsickle
 * @suppress {checkTypes,extraRequire,missingReturn,uselessCode} checked by tsc
 */
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

/**
 * @fileoverview added by tsickle
 * @suppress {checkTypes,extraRequire,missingReturn,uselessCode} checked by tsc
 */
var CountUpModule = /** @class */ (function () {
    function CountUpModule() {
    }
    CountUpModule.decorators = [
        { type: NgModule, args: [{
                    imports: [],
                    declarations: [CountUpDirective],
                    exports: [CountUpDirective]
                },] }
    ];
    return CountUpModule;
}());

/**
 * @fileoverview added by tsickle
 * @suppress {checkTypes,extraRequire,missingReturn,uselessCode} checked by tsc
 */

/**
 * @fileoverview added by tsickle
 * @suppress {checkTypes,extraRequire,missingReturn,uselessCode} checked by tsc
 */

export { CountUpModule, CountUpDirective as ɵa };

//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY291bnR1cC5qcy1hbmd1bGFyMi5qcy5tYXAiLCJzb3VyY2VzIjpbIm5nOi8vY291bnR1cC5qcy1hbmd1bGFyMi9saWIvY291bnQtdXAuZGlyZWN0aXZlLnRzIiwibmc6Ly9jb3VudHVwLmpzLWFuZ3VsYXIyL2xpYi9jb3VudC11cC5tb2R1bGUudHMiXSwic291cmNlc0NvbnRlbnQiOlsiaW1wb3J0IHtcbiAgRGlyZWN0aXZlLFxuICBFbGVtZW50UmVmLFxuICBJbnB1dCxcbiAgT3V0cHV0LFxuICBIb3N0TGlzdGVuZXIsXG4gIEV2ZW50RW1pdHRlcixcbiAgT25DaGFuZ2VzLFxuICBTaW1wbGVDaGFuZ2VzXG59IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xuaW1wb3J0ICogYXMgQ291bnRVcCBmcm9tICdjb3VudHVwLmpzJztcblxuQERpcmVjdGl2ZSh7XG4gIHNlbGVjdG9yOiAnW2NvdW50VXBdJ1xufSlcbmV4cG9ydCBjbGFzcyBDb3VudFVwRGlyZWN0aXZlIGltcGxlbWVudHMgT25DaGFuZ2VzIHtcblxuICBjb3VudFVwOiBhbnk7XG4gIC8vIE9wdGlvbmFsIGV4dHJhIGNvbmZpZ3VyYXRpb24sIHN1Y2ggYXMgZWFzaW5nLlxuICBASW5wdXQoJ2NvdW50VXAnKSBvcHRpb25zOiBhbnk7XG5cbiAgLy8gT3B0aW9uYWwgc3RhcnQgdmFsdWUgZm9yIHRoZSBjb3VudC4gRGVmYXVsdHMgdG8gemVyby5cbiAgQElucHV0KCkgc3RhcnRWYWw6IG51bWJlcjtcblxuICAvLyB0aGUgbnVtYmVyIHRvIGNvdW50IHRvXG4gIEBJbnB1dCgpIGVuZFZhbDogbnVtYmVyO1xuXG4gIC8vIE9wdGlvbmFsIGR1cmF0aW9uIG9mIHRoZSBhbmltYXRpb24gaW4gc2Vjb25kcy4gRGVmYXVsdCBpcyAyLlxuICBASW5wdXQoKSBkdXJhdGlvbjogbnVtYmVyO1xuXG4gIC8vIE9wdGlvbmFsIG51bWJlciBvZiBkZWNpbWFsIHBsYWNlcy4gRGVmYXVsdCBpcyAyLlxuICBASW5wdXQoKSBkZWNpbWFsczogbnVtYmVyO1xuICBcbiAgLy8gT3B0aW9uYWwgZmxhZyBmb3Igc3BlY2lmeWluZyB3aGV0aGVyIHRoZSBlbGVtZW50IHNob3VsZCByZS1hbmltYXRlIHdoZW4gY2xpY2tlZC5cbiAgLy8gRGVmYXVsdCBpcyB0cnVlLlxuICBASW5wdXQoKSByZWFuaW1hdGVPbkNsaWNrOiBib29sZWFuO1xuXG4gIC8vIFJlLWFuaW1hdGUgaWYgcHJlZmVyZW5jZSBpcyBzZXQuXG4gIEBIb3N0TGlzdGVuZXIoJ2NsaWNrJylcbiAgb25DbGljaygpIHtcbiAgICBpZiAodGhpcy5yZWFuaW1hdGVPbkNsaWNrKSB7XG4gICAgICB0aGlzLmFuaW1hdGUoKTtcbiAgICB9XG4gIH1cblxuICAvLyBvbi1jb21wbGV0ZSBldmVudCBlbWl0dGVyXG4gIEBPdXRwdXQoKSBjb21wbGV0ZSA9IG5ldyBFdmVudEVtaXR0ZXI8dm9pZD4oKTtcblxuICBjb25zdHJ1Y3Rvcihwcml2YXRlIGVsOiBFbGVtZW50UmVmKSB7fVxuXG4gIG5nT25DaGFuZ2VzKGNoYW5nZXM6IFNpbXBsZUNoYW5nZXMpIHtcbiAgICBpZiAoY2hhbmdlcy5lbmRWYWwgJiYgY2hhbmdlcy5lbmRWYWwuY3VycmVudFZhbHVlKSB7XG4gICAgICB0aGlzLmNvdW50VXAgPSB0aGlzLmNyZWF0ZUNvdW50VXAoKTtcbiAgICAgIHRoaXMuYW5pbWF0ZSgpO1xuICAgIH1cbiAgfVxuXG4gIHByaXZhdGUgY3JlYXRlQ291bnRVcCgpOiBhbnkge1xuICAgIGNvbnN0IHN0YXJ0ID0gdGhpcy5zdGFydFZhbCB8fCAwO1xuICAgIGNvbnN0IGR1cmF0aW9uID0gdGhpcy5kdXJhdGlvbiB8fCAyO1xuICAgIGNvbnN0IGRlY2ltYWxzID0gdGhpcy5kZWNpbWFscyB8fCAwO1xuXG4gICAgaWYgKCF0aGlzLmR1cmF0aW9uKSB7XG4gICAgICB0aGlzLmR1cmF0aW9uID0gZHVyYXRpb247XG4gICAgfVxuXG4gICAgLy8gY29uc3RydWN0IGNvdW50VXBcbiAgICBsZXQgY291bnRVcCA9IG5ldyBDb3VudFVwKHRoaXMuZWwubmF0aXZlRWxlbWVudCwgc3RhcnQsIHRoaXMuZW5kVmFsLCBkZWNpbWFscywgZHVyYXRpb24sIHRoaXMub3B0aW9ucyk7XG4gICAgY29uc3QgZGlmZiA9IE1hdGguYWJzKHRoaXMuZW5kVmFsIC0gc3RhcnQpO1xuICAgIC8vIG1ha2UgZWFzaW5nIHNtb290aGVyIGZvciBsYXJnZSBudW1iZXJzXG4gICAgaWYgKGRpZmYgPiA5OTkpIHtcbiAgICAgIGNvbnN0IHVwID0gKHRoaXMuZW5kVmFsID4gc3RhcnQpID8gLTEgOiAxO1xuICAgICAgY291bnRVcCA9IG5ldyBDb3VudFVwKHRoaXMuZWwubmF0aXZlRWxlbWVudCwgc3RhcnQsIHRoaXMuZW5kVmFsICsgKHVwICogMTAwKSwgZGVjaW1hbHMsIGR1cmF0aW9uIC8gMiwgdGhpcy5vcHRpb25zKTtcbiAgICB9XG5cbiAgICByZXR1cm4gY291bnRVcDtcbiAgfVxuXG4gIHByaXZhdGUgYW5pbWF0ZSgpIHtcbiAgICB0aGlzLmNvdW50VXAucmVzZXQoKTtcbiAgICBpZiAodGhpcy5lbmRWYWwgPiA5OTkpIHtcbiAgICAgIHRoaXMuY291bnRVcC5zdGFydCgoKSA9PiB0aGlzLmNvdW50VXAudXBkYXRlKHRoaXMuZW5kVmFsKSk7XG4gICAgICBzZXRUaW1lb3V0KCgpID0+IHtcbiAgICAgICAgdGhpcy5jb21wbGV0ZS5lbWl0KCk7XG4gICAgICB9LCB0aGlzLmR1cmF0aW9uICogMTAwMCk7XG4gICAgfSBlbHNlIHtcbiAgICAgIHRoaXMuY291bnRVcC5zdGFydCgoKSA9PiB7XG4gICAgICAgIHRoaXMuY29tcGxldGUuZW1pdCgpO1xuICAgICAgfSk7XG4gICAgfVxuICB9XG59XG4iLCJpbXBvcnQgeyBOZ01vZHVsZSB9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xuaW1wb3J0IHsgQ291bnRVcERpcmVjdGl2ZSB9IGZyb20gJy4vY291bnQtdXAuZGlyZWN0aXZlJztcblxuQE5nTW9kdWxlKHtcbiAgaW1wb3J0czogW10sXG4gIGRlY2xhcmF0aW9uczogW0NvdW50VXBEaXJlY3RpdmVdLFxuICBleHBvcnRzOiBbQ291bnRVcERpcmVjdGl2ZV1cbn0pXG5leHBvcnQgY2xhc3MgQ291bnRVcE1vZHVsZSB7IH1cbiJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7Ozs7O0FBQUE7SUFnREUsMEJBQW9CLEVBQWM7UUFBZCxPQUFFLEdBQUYsRUFBRSxDQUFZOztRQUZ4QixhQUFRLEdBQUcsSUFBSSxZQUFZLEVBQVEsQ0FBQztLQUVSOzs7Ozs7SUFUdEMsa0NBQU87Ozs7O0lBRFA7UUFFRSxJQUFJLElBQUksQ0FBQyxnQkFBZ0IsRUFBRTtZQUN6QixJQUFJLENBQUMsT0FBTyxFQUFFLENBQUM7U0FDaEI7S0FDRjs7Ozs7SUFPRCxzQ0FBVzs7OztJQUFYLFVBQVksT0FBc0I7UUFDaEMsSUFBSSxPQUFPLENBQUMsTUFBTSxJQUFJLE9BQU8sQ0FBQyxNQUFNLENBQUMsWUFBWSxFQUFFO1lBQ2pELElBQUksQ0FBQyxPQUFPLEdBQUcsSUFBSSxDQUFDLGFBQWEsRUFBRSxDQUFDO1lBQ3BDLElBQUksQ0FBQyxPQUFPLEVBQUUsQ0FBQztTQUNoQjtLQUNGOzs7O0lBRU8sd0NBQWE7OztJQUFyQjs7WUFDUSxLQUFLLEdBQUcsSUFBSSxDQUFDLFFBQVEsSUFBSSxDQUFDOztZQUMxQixRQUFRLEdBQUcsSUFBSSxDQUFDLFFBQVEsSUFBSSxDQUFDOztZQUM3QixRQUFRLEdBQUcsSUFBSSxDQUFDLFFBQVEsSUFBSSxDQUFDO1FBRW5DLElBQUksQ0FBQyxJQUFJLENBQUMsUUFBUSxFQUFFO1lBQ2xCLElBQUksQ0FBQyxRQUFRLEdBQUcsUUFBUSxDQUFDO1NBQzFCOzs7WUFHRyxPQUFPLEdBQUcsSUFBSSxPQUFPLENBQUMsSUFBSSxDQUFDLEVBQUUsQ0FBQyxhQUFhLEVBQUUsS0FBSyxFQUFFLElBQUksQ0FBQyxNQUFNLEVBQUUsUUFBUSxFQUFFLFFBQVEsRUFBRSxJQUFJLENBQUMsT0FBTyxDQUFDOztZQUNoRyxJQUFJLEdBQUcsSUFBSSxDQUFDLEdBQUcsQ0FBQyxJQUFJLENBQUMsTUFBTSxHQUFHLEtBQUssQ0FBQzs7UUFFMUMsSUFBSSxJQUFJLEdBQUcsR0FBRyxFQUFFOztnQkFDUixFQUFFLEdBQUcsQ0FBQyxJQUFJLENBQUMsTUFBTSxHQUFHLEtBQUssSUFBSSxDQUFDLENBQUMsR0FBRyxDQUFDO1lBQ3pDLE9BQU8sR0FBRyxJQUFJLE9BQU8sQ0FBQyxJQUFJLENBQUMsRUFBRSxDQUFDLGFBQWEsRUFBRSxLQUFLLEVBQUUsSUFBSSxDQUFDLE1BQU0sSUFBSSxFQUFFLEdBQUcsR0FBRyxDQUFDLEVBQUUsUUFBUSxFQUFFLFFBQVEsR0FBRyxDQUFDLEVBQUUsSUFBSSxDQUFDLE9BQU8sQ0FBQyxDQUFDO1NBQ3JIO1FBRUQsT0FBTyxPQUFPLENBQUM7S0FDaEI7Ozs7SUFFTyxrQ0FBTzs7O0lBQWY7UUFBQSxpQkFZQztRQVhDLElBQUksQ0FBQyxPQUFPLENBQUMsS0FBSyxFQUFFLENBQUM7UUFDckIsSUFBSSxJQUFJLENBQUMsTUFBTSxHQUFHLEdBQUcsRUFBRTtZQUNyQixJQUFJLENBQUMsT0FBTyxDQUFDLEtBQUssQ0FBQyxjQUFNLE9BQUEsS0FBSSxDQUFDLE9BQU8sQ0FBQyxNQUFNLENBQUMsS0FBSSxDQUFDLE1BQU0sQ0FBQyxHQUFBLENBQUMsQ0FBQztZQUMzRCxVQUFVLENBQUM7Z0JBQ1QsS0FBSSxDQUFDLFFBQVEsQ0FBQyxJQUFJLEVBQUUsQ0FBQzthQUN0QixFQUFFLElBQUksQ0FBQyxRQUFRLEdBQUcsSUFBSSxDQUFDLENBQUM7U0FDMUI7YUFBTTtZQUNMLElBQUksQ0FBQyxPQUFPLENBQUMsS0FBSyxDQUFDO2dCQUNqQixLQUFJLENBQUMsUUFBUSxDQUFDLElBQUksRUFBRSxDQUFDO2FBQ3RCLENBQUMsQ0FBQztTQUNKO0tBQ0Y7O2dCQTlFRixTQUFTLFNBQUM7b0JBQ1QsUUFBUSxFQUFFLFdBQVc7aUJBQ3RCOzs7Z0JBWkMsVUFBVTs7OzBCQWlCVCxLQUFLLFNBQUMsU0FBUzsyQkFHZixLQUFLO3lCQUdMLEtBQUs7MkJBR0wsS0FBSzsyQkFHTCxLQUFLO21DQUlMLEtBQUs7MEJBR0wsWUFBWSxTQUFDLE9BQU87MkJBUXBCLE1BQU07O0lBNkNULHVCQUFDO0NBL0VEOzs7Ozs7QUNaQTtJQUdBO0tBSzhCOztnQkFMN0IsUUFBUSxTQUFDO29CQUNSLE9BQU8sRUFBRSxFQUFFO29CQUNYLFlBQVksRUFBRSxDQUFDLGdCQUFnQixDQUFDO29CQUNoQyxPQUFPLEVBQUUsQ0FBQyxnQkFBZ0IsQ0FBQztpQkFDNUI7O0lBQzRCLG9CQUFDO0NBTDlCOzs7Ozs7Ozs7Ozs7OzsifQ==