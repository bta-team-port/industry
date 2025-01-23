package teamport.industry.core.interfaces;

public interface IVoltageHealth {
    int industry$getHealth();
    void industry$setHealth(int amount);
    void industry$modifyHealth(int amount);
}
