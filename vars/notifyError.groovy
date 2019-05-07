import components.Slack

def call() {
    env.SUCCESS = "false";
    
    try {
        def slack = new Slack(this);

        attachment = [
            [
                color: "danger",
                fields: [
                    [
                        value: "@here, *<${env.GIT_REPO_URL}|${env.GIT_REPO_NAME}>/<${env.GIT_BRANCH_URL}|${env.GIT_BRANCH_NAME}>* - <${env.RUN_DISPLAY_URL}|build #${env.BUILD_NUMBER}> failed :face_with_monocle:"
                    ]
                ],
                markdown: ["pretext"]
            ],
            [
                color: "danger",
                fallback: "",
                actions: [
                    [
                        type: "button",
                        text: "Jenkins",
                        url: env.RUN_DISPLAY_URL
                    ]
                ]
            ]
        ];

        // Post message in Slack thread and broadcast to channel
        slack.postAttachmentAndBroadcast(env.BUILD_LOG_SLACK_THREAD, attachment);
        } catch (err) {
            slackSend(message: "@here, <${env.RUN_DISPLAY_URL}|build #${env.BUILD_NUMBER}> failed :face_with_monocle:");
        }

    // Return build start date
    return new Date(env.BUILD_START);
}
