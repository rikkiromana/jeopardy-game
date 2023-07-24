public class Question {
    private String category;
    private String questionText;
    private String[] choices;
    private int correctAnswerIndex;
    private int points;
    private boolean answeredCorrectly;

    public Question(String category, String questionText, String[] choices, int correctAnswerIndex, int points) {
        this.category = category;
        this.questionText = questionText;
        this.choices = choices;
        this.correctAnswerIndex = correctAnswerIndex;
        this.points = points;
        this.answeredCorrectly = false;
    }

    public String getCategory() {
        return category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getChoices() {
        return choices;
    }

    public int getPoints() {
        return points;
    }

    public boolean isAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void markAnsweredCorrectly() {
        answeredCorrectly = true;
    }

    public void markAnsweredIncorrectly() {
        answeredCorrectly = false;
    }

    public boolean isCorrectAnswer(int answerIndex) {
        return answerIndex == correctAnswerIndex;
    }
}
