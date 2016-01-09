package se.chalmers.halfwaytospirit.waveapp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * This class defines the avatar image view.
 */
public class AvatarView extends ImageView {
    private static final int[] STATE_EMPTY = {R.attr.state_empty};
    private static final int[] STATE_SITTING = {R.attr.state_sitting};
    private static final int[] STATE_WAVING1 = {R.attr.state_waving1};
    private static final int[] STATE_WAVING2 = {R.attr.state_waving2};
    private static final int[] STATE_WAVING3 = {R.attr.state_waving3};
    private static final int[] STATE_DEAD = {R.attr.state_dead};


    private boolean isEmpty = false;
    private boolean isSitting = false;
    private boolean isWaving = false;
    private boolean isDead = false;

    /**
     * Constructor.
     * @param context - the context.
     */
    public AvatarView(Context context) {
        super(context);
    }

    /**
     * Constructor.
     * @param context - the context.
     * @param attrs - the attributes.
     */
    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor.
     * @param context - the context.
     * @param attrs - the attributes.
     * @param defStyleAttr - the style definition.
     */
    public AvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Called when the possible states of the view are created. This override creates a number
     * of custom states.
     * @param extraSpace - the extra space.
     * @return the set of drawable states.
     */
    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 2);

        if(isEmpty) {
            mergeDrawableStates(drawableState, STATE_EMPTY);
        }

        if(isSitting) {
            mergeDrawableStates(drawableState, STATE_SITTING);
        }

        if(isWaving) {
            mergeDrawableStates(drawableState, STATE_WAVING3);
        }

        if(isDead) {
            mergeDrawableStates(drawableState, STATE_DEAD);
        }

        return drawableState;
    }

    /**
     * Sets whether the avatar seat is empty.
     * @param isEmpty - whether empty or not.
     */
    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    /**
     * Sets whether the avatar is sitting.
     * @param isSitting - whether sitting or not.
     */
    public void setIsSitting(boolean isSitting) {
        this.isSitting = isSitting;
    }

    /**
     * Sets whether the avatar is waving.
     * @param isWaving - whether waving or not.
     */
    public void setIsWaving(boolean isWaving) {
        this.isWaving = isWaving;
    }

    /**
     * Set whether the avatar is dead.
     * @param isDead - whether dead or not.
     */
    public void setIsDead(boolean isDead) { this.isDead = isDead; }
}
