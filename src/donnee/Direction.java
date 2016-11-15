package donnee;

public enum Direction {
	NORD,
	SUD,
	EST,
	OUEST;
        
        private Direction oppose;
        
        static {
            NORD.oppose = SUD;
            SUD.oppose = NORD;
            EST.oppose = OUEST;
            OUEST.oppose = EST;
        }
        public Direction inverserDir() {
            return oppose;
        }

}
