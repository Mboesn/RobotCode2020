package frc.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.Filesystem;

/**
 * Paths of music files (.chrp) to be played by the six Talon FXs of the Drivetrain.
 * New files can be converted from MIDI format to CHRP format using Phoenix Tuner's Music Chirp Generator tab.
 */
public enum Song {
    Star_Wars_Main_Theme,
    Undertale_Megalovania,
    Twentieth_Century_Fox,
    Rasputin,
    Animal_Crossing_Nook_Scranny,
    Kid_Francescoli_Moon,
    Fail,
    Success,
    Running_in_the_90s,
    Soviet_union,
    I_got_a_feeling,
    Avengers_theme,
    Another_one_bites_the_dust,
    Starwars_cantina,
    Duel_of_the_fates,
    Eye_of_the_tiger,
    Indiana_jones,
    We_are_the_champions,
    Im_a_believer,
    Crab_rave,
    Bohemian_rhapsody,
    Starwars_theme_and_twentieth_century_fox,
    Tetris,
    How_i_met_your_mother,
    All_star,
    Jerusalem_of_gold,
    Pirates_Of_The_Caribbean,
    Hatikva,
    Mario,
    Superman,
    Aint_No_Mountain_High_Enough,
    Hooked_on_a_feeling,
    Gravity_falls,
    Friends,
    Mizrach,
    Mr_blue_sky,
    Escape,
    Harry_potter,
    Come_and_get_your_love,
    We_will_rock_you;

    private final String path;

    Song() {
        path = Filesystem.getDeployDirectory() + "/music/" + name() + ".chrp";
    }
    
    Song(String path) {
        this.path = Filesystem.getDeployDirectory() + "/music/" + path + ".chrp";
    }

    public String getPath() {
        return path;
    }
}
