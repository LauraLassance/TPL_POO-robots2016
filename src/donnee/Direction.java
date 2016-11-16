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
        
        /**
         * Methode permettant d'inverser la direction.
         * @return la direction opposée.
         */
        public Direction inverserDir() {
            return oppose;
        }

}
