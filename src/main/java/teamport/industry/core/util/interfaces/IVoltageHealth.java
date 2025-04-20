package teamport.industry.core.util.interfaces;

public interface IVoltageHealth {
    int industry$getHealth();
    void industry$setHealth(int amount);
    void industry$modifyHealth(int amount);
}
