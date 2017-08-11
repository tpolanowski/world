package world.game.graphics.weather;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WeatherEffectLoader {
    private static WeatherEffect weatherEffectFog;
    private static WeatherEffect weatherEffectSnow;
    private static WeatherEffect weatherEffectRain;
    private static WeatherEffect weatherEffectSun;

    static {
        weatherEffectFog = new WeatherEffect("fog.particle", WeatherEffectType.FOG);
        weatherEffectSnow = new WeatherEffect("snow.particle", WeatherEffectType.SNOW);
        weatherEffectRain = new WeatherEffect("rain.particle", WeatherEffectType.RAIN);
        weatherEffectSun = new WeatherEffect("sun.particle", WeatherEffectType.SUN);
    }

    public static void drawSnow(SpriteBatch batch, float delta){
        weatherEffectSnow.draw(batch, delta);
    }

    public static void drawRain(SpriteBatch batch, float delta){
        weatherEffectRain.draw(batch, delta);
    }

    public static void drawSun(SpriteBatch batch, float delta){
        weatherEffectSun.draw(batch, delta);
    }

    public static void drawFog(SpriteBatch batch, float delta){
        weatherEffectFog.draw(batch, delta);
    }

}
