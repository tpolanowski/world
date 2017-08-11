package world.game.graphics.utils;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import world.game.graphics.utils.SpecialEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Pool;

public class SpecialEffectPool extends Pool<PooledEffect> {
    private final ParticleEffect effect;

    public SpecialEffectPool (ParticleEffect effect, int initialCapacity, int max) {
        super(initialCapacity, max);
        this.effect = effect;
    }

    protected PooledEffect newObject () {
        return new PooledEffect(effect);
    }

    public PooledEffect obtain () {
        PooledEffect effect = super.obtain();
        effect.reset();
        return effect;
    }

    public class PooledEffect extends ParticleEffect {
        PooledEffect (ParticleEffect effect) {
            super(effect);
        }

        @Override
        public void reset () {
            super.reset();
        }

        public void free () {
            SpecialEffectPool.this.free(this);
        }
    }
}
